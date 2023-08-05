package com.creator.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    //用户名
    private String userName;
    //昵称
    private String nickName;
    //密码
    private String password;
    //邮箱
    private String email;
}
