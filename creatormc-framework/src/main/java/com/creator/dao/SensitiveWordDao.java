package com.creator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.creator.domain.entity.SensitiveWord;
import org.apache.ibatis.annotations.Mapper;

/**
 * (SensitiveWord)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-09 16:14:47
 */
@Mapper
public interface SensitiveWordDao extends BaseMapper<SensitiveWord> {

}

