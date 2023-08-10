package com.creator.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAllVo {
    //id
    private Long id;
    //分类名
    private String name;
    //描述
    private String description;
}
