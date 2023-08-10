package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.dao.TagDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.TagListDto;
import com.creator.domain.entity.Tag;
import com.creator.domain.vo.PageVo;
import com.creator.domain.vo.TagVo;
import com.creator.service.TagService;
import com.creator.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

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
    public ResponseResult adTag(TagListDto tagListDto) {
        Tag tag = new Tag();
        tag.setName(tagListDto.getName());
        tag.setRemark(tagListDto.getRemark());
        save(tag);
        return ResponseResult.okResult();
    }
}

