package com.creator.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    private Long id;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid（用于确定回复的是哪个用户）
    private Long toCommentUserId;
    //所回复的目标评论的用户名
    private String toCommentUserName;
    //回复目标评论id（用于确定回复的是哪个评论）
    private Long toCommentId;

    private Long createBy;

    private Date createTime;
    //评论人名字
    private String username;
    //评论人头像链接
    private String avatar;
    //子评论列表
    private List<CommentVo> children;
}
