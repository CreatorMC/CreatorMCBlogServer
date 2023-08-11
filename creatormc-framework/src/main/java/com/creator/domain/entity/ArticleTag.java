package com.creator.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章标签关联表(ArticleTag)表实体类
 *
 * @author makejava
 * @since 2023-08-11 10:08:28
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTag {
    //文章id
    private Long articleId;
    //标签id
    private Long tagId;
}

