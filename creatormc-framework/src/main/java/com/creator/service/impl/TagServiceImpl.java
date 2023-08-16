package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.dao.TagDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.TagListDto;
import com.creator.domain.dto.UpdateTagDto;
import com.creator.domain.entity.Tag;
import com.creator.domain.vo.PageVo;
import com.creator.domain.vo.TagAllVo;
import com.creator.domain.vo.TagVo;
import com.creator.service.TagService;
import com.creator.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-08-09 15:23:22
 */
@SuppressWarnings("rawtypes")
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService {

    @Override
    public ResponseResult getTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        Page<Tag> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        if(!Objects.isNull(tagListDto)) {
            queryWrapper.eq(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
            queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());
        }
        page(page,queryWrapper);
        PageVo pageVo = new PageVo(BeanCopyUtils.copyBeanList(page.getRecords(), TagVo.class), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(TagListDto tagListDto) {
        Tag tag = new Tag();
        tag.setName(tagListDto.getName());
        tag.setRemark(tagListDto.getRemark());
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long[] id) {
        List<Tag> tags = Arrays.stream(id).map(aLong -> {
            Tag tag = new Tag();
            tag.setId(aLong);
            return tag;
        }).collect(Collectors.toList());
        removeByIds(tags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTag(Long id) {
        Tag tag = getById(id);
        return ResponseResult.okResult(BeanCopyUtils.copyBean(tag, TagVo.class));
    }

    @Override
    public ResponseResult updateTag(UpdateTagDto dto) {
        Tag tag = new Tag();
        tag.setId(dto.getId());
        tag.setName(dto.getName());
        tag.setRemark(dto.getRemark());
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getAllTag() {
        List<Tag> tagList = list();
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(tagList, TagAllVo.class));
    }
}

