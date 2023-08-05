package com.creator.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInfoDto {
    //邮箱
    private String email;
    //昵称
    private String nickName;
    //用户性别（0男，1女，2未知）
    private String sex;
}
