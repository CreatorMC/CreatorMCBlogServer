package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.constants.SystemConstants;
import com.creator.dao.MenuDao;
import com.creator.dao.UserRoleDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.MenuListDto;
import com.creator.domain.entity.Menu;
import com.creator.domain.entity.Role;
import com.creator.domain.entity.RoleMenu;
import com.creator.domain.entity.UserRole;
import com.creator.domain.vo.MenuAdminListVo;
import com.creator.service.MenuService;
import com.creator.utils.BeanCopyUtils;
import com.creator.utils.SecurityUtils;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-08-07 08:43:17
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public List<String> selectMenuPermsByUserId(Long userId) {
        //这里不能使用SecurityUtils.isAdmin()，因为这个方法是从SecurityContextHolder中获取的用户id，这个方法在封装LoginUser阶段也会被调用
        //而在封装LoginUser的这个阶段，SecurityContextHolder中还没有LoginUser，自然也就获取不到用户id
        if(SecurityUtils.ADMIN_USER_ID.equals(userId)) {
            //是超级管理员，返回所有权限
            List<Menu> menuList = list(new LambdaQueryWrapper<Menu>()
                    .in(Menu::getMenuType, SystemConstants.MENU_TYPE_MENU, SystemConstants.MENU_TYPE_BUTTON)
                    .eq(Menu::getStatus, SystemConstants.MENU_STATUS_NORMAL)
            );
            return menuList.stream().map(Menu::getPerms).collect(Collectors.toList());
        }
        return userRoleDao.selectJoinList(String.class, new MPJLambdaWrapper<UserRole>()
                //去重，一个用户可以有多个角色，每个角色都可以有多个权限，部分角色的权限可能有重复的，所以要去重
                .distinct()
                .select(Menu::getPerms)
                .innerJoin(Role.class, Role::getId, UserRole::getRoleId)
                .innerJoin(RoleMenu.class, RoleMenu::getRoleId, Role::getId)
                .innerJoin(Menu.class, Menu::getId, RoleMenu::getMenuId)
                .eq(UserRole::getUserId, userId)
                //角色状态为正常
                .eq(Role::getStatus, SystemConstants.ROLE_STATUS_NORMAL)
                //只查找C和F类型的菜单
                .in(Menu::getMenuType, SystemConstants.MENU_TYPE_MENU, SystemConstants.MENU_TYPE_BUTTON)
                //菜单状态为正常
                .eq(Menu::getStatus, SystemConstants.MENU_STATUS_NORMAL)
        );
    }

    @Override
    public List<Menu> selectRouters(Long userId) {
        if(SecurityUtils.isAdmin()) {
            //是超级管理员，返回所有菜单
            return list(new LambdaQueryWrapper<Menu>()
                    //只查找M和C类型的菜单
                    .in(Menu::getMenuType, SystemConstants.MENU_TYPE_CATALOG, SystemConstants.MENU_TYPE_MENU)
                    //菜单状态为正常
                    .eq(Menu::getStatus, SystemConstants.MENU_STATUS_NORMAL)
                    //先根据父菜单id排序，再根据排序字段排序
                    .orderByAsc(Menu::getParentId, Menu::getOrderNum)
            );
        }
        return userRoleDao.selectJoinList(Menu.class, new MPJLambdaWrapper<UserRole>()
                .distinct()
                .selectAll(Menu.class)
                .innerJoin(Role.class, Role::getId, UserRole::getRoleId)
                .innerJoin(RoleMenu.class, RoleMenu::getRoleId, Role::getId)
                .innerJoin(Menu.class, Menu::getId, RoleMenu::getMenuId)
                //查找指定用户
                .eq(UserRole::getUserId, userId)
                //角色状态为正常
                .eq(Role::getStatus, SystemConstants.ROLE_STATUS_NORMAL)
                //菜单状态为正常
                .eq(Menu::getStatus, SystemConstants.MENU_STATUS_NORMAL)
                //只查找M和C类型的菜单
                .in(Menu::getMenuType, SystemConstants.MENU_TYPE_CATALOG, SystemConstants.MENU_TYPE_MENU)
                //先根据父菜单id排序，再根据排序字段排序
                .orderByAsc(Menu::getParentId, Menu::getOrderNum)
        );
    }

    @Override
    public ResponseResult getMenuList(MenuListDto menuListDto) {
        List<Menu> menus = list(new LambdaQueryWrapper<Menu>()
                //模糊查找
                .like(StringUtils.hasText(menuListDto.getStatus()), Menu::getStatus, menuListDto.getStatus())
                .like(StringUtils.hasText(menuListDto.getMenuName()), Menu::getMenuName, menuListDto.getMenuName())
                //先按父菜单id排序，再按orderNum排序，都是升序排序
                .orderByAsc(Menu::getParentId, Menu::getOrderNum)
        );
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(menus, MenuAdminListVo.class));
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenu(Long id) {
        Menu menu = getById(id);
        return ResponseResult.okResult(BeanCopyUtils.copyBean(menu, MenuAdminListVo.class));
    }
}

