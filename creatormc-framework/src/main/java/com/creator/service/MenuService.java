package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.MenuListDto;
import com.creator.domain.entity.Menu;

import java.util.List;

/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-08-07 08:43:17
 */
@SuppressWarnings("rawtypes")
public interface MenuService extends IService<Menu> {

    List<String> selectMenuPermsByUserId(Long userId);

    List<Menu> selectRouters(Long userId);

    /**
     * 后台获取菜单列表
     * @param menuListDto
     * @return
     */
    ResponseResult getMenuList(MenuListDto menuListDto);
}

