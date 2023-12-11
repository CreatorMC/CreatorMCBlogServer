package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.annotation.SensitiveWordFilter;
import com.creator.constants.SystemConstants;
import com.creator.dao.CommentDao;
import com.creator.dao.UserDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.GetPageCommentListDto;
import com.creator.domain.entity.Comment;
import com.creator.domain.entity.User;
import com.creator.domain.vo.*;
import com.creator.enums.AppHttpCodeEnum;
import com.creator.exception.SystemException;
import com.creator.service.ArticleService;
import com.creator.service.CommentService;
import com.creator.service.UserService;
import com.creator.utils.BeanCopyUtils;
import com.creator.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-07-25 22:59:16
 */
@SuppressWarnings("rawtypes")
@Service("commentService")
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //构造条件
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                //此文章下的评论
                .eq(SystemConstants.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId)
                //这条评论是根评论
                .eq(Comment::getRootId, SystemConstants.COMMENT_ROOT_ID)
                //这条评论的类型
                .eq(Comment::getType, commentType)
                //降序排列，最新的评论会在最前面
                .orderByDesc(Comment::getCreateTime);
        //分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        //封装
        List<CommentVo> commentVos = getCommentVos(page.getRecords());

        //查询所有根评论对应的子评论集合
        commentVos = commentVos.stream().peek(commentVo -> {
            LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Comment::getRootId, commentVo.getId())
                    //子评论按时间升序，新的子评论会在最后
                    .orderByAsc(Comment::getCreateTime);
            commentVo.setChildren(getCommentVos(list(lambdaQueryWrapper)));
        }).collect(Collectors.toList());

        PageVo pageVo = new PageVo(commentVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @SensitiveWordFilter
    public ResponseResult addComment(Comment comment) {
        //检查此用户是否被封禁，封禁后不能评论
        Date banEndTime = userService.getById(SecurityUtils.getUserId()).getBanEndTime();
        if(!Objects.isNull(banEndTime)) {
            //封禁结束时间不为空
            Date now = new Date();
            if(now.compareTo(banEndTime) < 0) {
                //此用户被封禁了
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SystemConstants.USER_BAN_MESSAGE);
                return ResponseResult.errorResult(AppHttpCodeEnum.USER_BAN.getCode(),
                        String.format(AppHttpCodeEnum.USER_BAN.getMsg(), simpleDateFormat.format(banEndTime)));
            }
        }
        //评论不能为空
        if(!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getPageCommentList(Integer pageNum, Integer pageSize, GetPageCommentListDto dto) {
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, new LambdaQueryWrapper<Comment>()
                //根据评论内容模糊查询
                .like(StringUtils.hasText(dto.getContent()), Comment::getContent, dto.getContent())
                //根据文章id查询
                .eq(!Objects.isNull(dto.getArticleId()), Comment::getArticleId, dto.getArticleId())
                //根据用户查询
                .eq(!Objects.isNull(dto.getCreateBy()), Comment::getCreateBy, dto.getCreateBy())
        );
        List<Comment> comments = page.getRecords();
        List<CommentAdminListVo> commentAdminListVos = comments.stream().map(comment -> {
            ResponseResult articleResponse = articleService.getArticle(comment.getArticleId());
            String articleTitle = AppHttpCodeEnum.ARTICLE_IS_NULL.getMsg();
            if(!articleResponse.getCode().equals(AppHttpCodeEnum.ARTICLE_IS_NULL.getCode())) {
                //文章查到了，获得文章标题
                articleTitle = ((ArticleVo)articleResponse.getData()).getTitle();
            }
            if(comment.getType().equals(SystemConstants.LINK_COMMENT)) {
                //如果是友链评论，设置文章标题为 "友情链接"
                articleTitle = SystemConstants.LINK_COMMENT_TITLE;
            }
            //如果查询不到对应的用户，getUser 会引发异常，异常会被全局异常处理捕获到。
            ResponseResult userResponse = userService.getUserInfo(comment.getCreateBy());
            //获得发送者的昵称
            String createName = ((UserInfoVo)userResponse.getData()).getNickName();
            return new CommentAdminListVo(
                    comment.getId(),
                    comment.getContent(),
                    comment.getArticleId(),
                    articleTitle,
                    comment.getCreateBy(),
                    createName,
                    comment.getCreateTime()
            );
        }).collect(Collectors.toList());
        return ResponseResult.okResult(new PageVo(commentAdminListVos, page.getTotal()));
    }

    @Override
    public ResponseResult deleteComment(List<Long> ids) {
        removeByIds(ids);
        return ResponseResult.okResult();
    }

    /**
     * 得到封装好的CommentVo列表
     * @param comments List
     * @return List
     */
    private List<CommentVo> getCommentVos(List<Comment> comments) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(comments, CommentVo.class);

        commentVos = commentVos.stream().peek(commentVo -> {

            User userSelf = userDao.selectById(commentVo.getCreateBy());
            //注意显示的是昵称
            commentVo.setUsername(userSelf.getNickName());
            //设置头像链接
            commentVo.setAvatar(userSelf.getAvatar());

            Long toCommentUserId = commentVo.getToCommentUserId();
            if (!SystemConstants.TO_COMMENT_USER_ID_NULL.equals(toCommentUserId)) {
                commentVo.setToCommentUserName(userDao.selectById(toCommentUserId).getNickName());
            }

        }).collect(Collectors.toList());
        return commentVos;
    }
}

