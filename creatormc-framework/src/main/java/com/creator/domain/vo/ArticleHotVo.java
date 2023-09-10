package com.creator.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleHotVo {
    private Long id;
    //标题
    public String title;
    //访问量
    private Long viewCount;
    //缩略图
    private String thumbnail;
}
