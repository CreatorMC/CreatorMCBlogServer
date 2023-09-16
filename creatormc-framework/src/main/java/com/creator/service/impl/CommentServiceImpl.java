package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.constants.SystemConstants;
import com.creator.dao.CommentDao;
import com.creator.dao.UserDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.entity.Comment;
import com.creator.domain.entity.User;
import com.creator.domain.vo.CommentVo;
import com.creator.domain.vo.PageVo;
import com.creator.enums.AppHttpCodeEnum;
import com.creator.exception.SystemException;
import com.creator.service.CommentService;
import com.creator.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
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
            lambdaQueryWrapper.eq(Comment::getToCommentId, commentVo.getId())
                    //子评论按时间升序，新的子评论会在最后
                    .orderByAsc(Comment::getCreateTime);
            commentVo.setChildren(getCommentVos(list(lambdaQueryWrapper)));
        }).collect(Collectors.toList());

        PageVo pageVo = new PageVo(commentVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if(!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
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

