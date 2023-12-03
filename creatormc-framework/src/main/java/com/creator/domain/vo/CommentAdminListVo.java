package com.creator.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentAdminListVo {
    //评论id
    private Long id;
    //评论内容
    private String content;
    //所属文章id
    private Long articleId;
    //文章标题
    private String articleTitle;
    //发送者id
    private Long createBy;
    //发送者用户名
    private String createName;
    //评论时间
    private Date createTime;
}
