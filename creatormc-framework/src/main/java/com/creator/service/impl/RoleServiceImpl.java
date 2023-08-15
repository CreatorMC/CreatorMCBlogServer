package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.constants.SystemConstants;
import com.creator.dao.RoleDao;
import com.creator.dao.UserRoleDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.AddRoleDto;
import com.creator.domain.dto.RoleListDto;
import com.creator.domain.dto.UpdateRoleDto;
import com.creator.domain.entity.Role;
import com.creator.domain.entity.RoleMenu;
import com.creator.domain.entity.UserRole;
import com.creator.domain.vo.PageVo;
import com.creator.domain.vo.RoleAdminVo;
import com.creator.service.RoleMenuService;
import com.creator.service.RoleService;
import com.creator.utils.BeanCopyUtils;
import com.creator.utils.SecurityUtils;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-08-06 22:20:33
 */
@SuppressWarnings("rawtypes")
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeysByUserId(Long userId) {
        if(SecurityUtils.isAdmin()) {
            //是超级管理员，和前端商量只需要返回admin即可
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add(SystemConstants.ADMIN);
            return  roleKeys;
        }
        return userRoleDao.selectJoinList(String.class, new MPJLambdaWrapper<UserRole>()
                .select(Role::getRoleKey)
                .innerJoin(Role.class, Role::getId, UserRole::getRoleId)
                .eq(UserRole::getUserId, userId)
                //角色状态为正常
                .eq(Role::getStatus, SystemConstants.ROLE_STATUS_NORMAL)
        );
    }

    @Override
    public ResponseResult getPageRoleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto) {
        //分页查询
        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, new LambdaQueryWrapper<Role>()
                //根据角色名进行模糊查询
                .like(StringUtils.hasText(roleListDto.getRoleName()), Role::getRoleName, roleListDto.getRoleName())
                //根据状态进行查询
                .eq(StringUtils.hasText(roleListDto.getStatus()), Role::getStatus, roleListDto.getStatus())
                //按照role_sort进行升序排列
                .orderByAsc(Role::getRoleSort)
        );
        return ResponseResult.okResult(new PageVo(page.getRecords(), page.getTotal()));
    }

    @Override
    public ResponseResult changeRoleStatus(Role role) {
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional  //开启事务
    public ResponseResult addRole(AddRoleDto addRoleDto) {
        //保存角色到数据库
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        save(role);
        //将角色与菜单建立对应关系
        List<Long> menuIds = addRoleDto.getMenuIds();
        List<RoleMenu> roleMenus = menuIds.stream().map(menuId -> new RoleMenu(role.getId(), menuId)).collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRole(Long id) {
        Role role = getById(id);
        return ResponseResult.okResult(BeanCopyUtils.copyBean(role, RoleAdminVo.class));
    }

    @Override
    @Transactional  //开启事务
    public ResponseResult updateRole(UpdateRoleDto updateRoleDto) {
        //更新角色
        Role role = BeanCopyUtils.copyBean(updateRoleDto, Role.class);
        updateById(role);
        //删除原来角色与菜单权限的关联
        roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getRoleId, role.getId())
        );
        //添加现在角色与菜单权限的关联
        List<RoleMenu> roleMenus = updateRoleDto.getMenuIds().stream().map(menuId -> new RoleMenu(role.getId(), menuId)).collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}

