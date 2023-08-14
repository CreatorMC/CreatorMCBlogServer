package com.creator.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuAdminTreeVo {
    //菜单id
    private Long id;
    //菜单名称，在 Menu 实体中为 menuName
    private String label;
    //父菜单ID
    private Long parentId;
    //子菜单列表
    private List<MenuAdminTreeVo> children;
}
