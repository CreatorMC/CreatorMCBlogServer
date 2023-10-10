package com.creator.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 更新用户密码dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPasswordDto {
    //邮箱
    String email;
    //验证码
    String vCode;
    //密码
    String password;
    //Redis 的验证码的 key
    String uuid;
}
