package com.creator.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPageCommentListDto {
    //评论内容
    private String content;
    //文章id
    private Long articleId;
    //评论的用户id
    private Long createBy;
}
