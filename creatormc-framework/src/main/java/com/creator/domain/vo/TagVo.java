package com.creator.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {
    //id
    private Long id;
    //标签名
    private String name;
    //备注
    private String remark;
}
