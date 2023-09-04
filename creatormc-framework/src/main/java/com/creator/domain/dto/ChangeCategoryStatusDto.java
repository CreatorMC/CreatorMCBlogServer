package com.creator.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeCategoryStatusDto {
    //分类id
    private Long id;
    //分类状态（0正常 1停用）
    private String status;
}
