package com.creator.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTagDto {
    //id
    private Long id;
    //标签名
    private String name;
    //标签备注
    private String remark;
}
