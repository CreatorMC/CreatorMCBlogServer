package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.GetPageLinkListDto;
import com.creator.domain.entity.Link;

import java.util.List;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-07-24 16:03:35
 */
@SuppressWarnings("rawtypes")
public interface LinkService extends IService<Link> {

    /**
     * 获取审核通过的友链
     * @return
     */
    ResponseResult getAllLink();

    /**
     * 分页查询友链列表
     * @param pageNum 第几页
     * @param pageSize 每页几条记录
     * @param dto
     * @return
     */
    ResponseResult getPageLinkList(Integer pageNum, Integer pageSize, GetPageLinkListDto dto);

    /**
     * 添加友链
     * @param link
     * @return
     */
    ResponseResult addLink(Link link);

    /**
     * 查询单个友链
     * @param id 友链 id
     * @return
     */
    ResponseResult getLink(Long id);

    /**
     * 更新友链
     * @param link
     * @return
     */
    ResponseResult updateLink(Link link);

    /**
     * 删除友链
     * @param id 友链 id
     * @return
     */
    ResponseResult deleteLink(List<Long> id);
}

