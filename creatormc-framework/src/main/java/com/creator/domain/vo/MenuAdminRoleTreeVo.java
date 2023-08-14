package com.creator.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuAdminRoleTreeVo {
    //菜单树
    List<MenuAdminTreeVo> menus;
    //角色所关联的菜单权限id列表
    List<Long> checkedKeys;
}
