package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.GetPageSensitiveListDto;
import com.creator.domain.entity.SensitiveWord;

import java.util.List;

/**
 * (SensitiveWord)表服务接口
 *
 * @author makejava
 * @since 2023-12-09 16:14:47
 */
@SuppressWarnings("rawtypes")
public interface SensitiveWordService extends IService<SensitiveWord> {

    /**
     * 分页查询敏感词列表
     * @param pageNum
     * @param pageSize
     * @param dto
     * @return
     */
    ResponseResult getPageSensitiveList(Integer pageNum, Integer pageSize, GetPageSensitiveListDto dto);

    /**
     * 添加敏感词
     * @param sensitiveWord
     * @return
     */
    ResponseResult addSensitive(SensitiveWord sensitiveWord);

    /**
     * 更新敏感词
     * @param sensitiveWord
     * @return
     */
    ResponseResult updateSensitive(SensitiveWord sensitiveWord);

    /**
     * 删除敏感词
     * @param ids
     * @return
     */
    ResponseResult deleteSensitive(List<Long> ids);

    /**
     * 查询单个敏感词
     * @param id
     * @return
     */
    ResponseResult getSensitive(Long id);
}

