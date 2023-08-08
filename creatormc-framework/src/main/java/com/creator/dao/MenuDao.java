package com.creator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.creator.domain.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-08-07 08:43:17
 */
@Mapper
public interface MenuDao extends BaseMapper<Menu> {

}

