package com.creator.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserInfoVo {
    //权限列表
    List<String> permissions;
    //角色列表
    List<String> roles;
    //用户信息
    UserInfoVo user;
}
