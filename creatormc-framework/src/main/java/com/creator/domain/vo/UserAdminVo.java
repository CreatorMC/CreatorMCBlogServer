package com.creator.domain.vo;

import com.creator.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAdminVo {
    //用户所关联的角色id列表
    List<Long> roleIds;
    //所有角色的列表
    List<Role> roles;
    //用户信息
    UserAdminGetVo user;
}
