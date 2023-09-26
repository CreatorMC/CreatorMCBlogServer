package com.creator.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (ArticleUserLike)表实体类
 *
 * @author makejava
 * @since 2023-09-24 21:26:09
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleUserLike {
    //文章id
    private Long articleId;
    //用户id
    private Long userId;
}

