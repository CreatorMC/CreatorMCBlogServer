package com.creator.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSensitiveDto {
    //敏感词
    private String content;
    //敏感词类型（1：白名单，0：黑名单）
    private String type;
}
