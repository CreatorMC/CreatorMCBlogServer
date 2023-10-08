package com.creator.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSearchVo {
    //文章id
    private String id;
    //文章标题（若标题不在高亮字段中，则使用此字段显示标题）
    private String title;
    //高亮字段
    private Map<String, List<String>> highlightFields;
}
