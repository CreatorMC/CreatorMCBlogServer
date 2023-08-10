package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.TagDto;
import com.creator.domain.dto.TagListDto;
import com.creator.domain.entity.Tag;

/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-08-09 15:23:22
 */
@SuppressWarnings("rawtypes")
public interface TagService extends IService<Tag> {

    ResponseResult getTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult adTag(TagListDto tagListDto);

    ResponseResult deleteTag(Long[] id);

    ResponseResult getTag(Long id);

    ResponseResult upDataTag(TagDto tagDto);
}

