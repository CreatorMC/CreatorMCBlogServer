package com.creator.vo;

import lombok.Data;

@Data
public class HotArticleVo {
    private Long id;
    //标题
    public String title;
    //访问量
    private Long viewCount;
}
