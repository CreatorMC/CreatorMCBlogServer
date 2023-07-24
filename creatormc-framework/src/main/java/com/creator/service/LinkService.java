package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.domain.ResponseResult;
import com.creator.domain.entity.Link;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-07-24 16:03:35
 */
public interface LinkService extends IService<Link> {

    /**
     * 获取审核通过的友链
     * @return
     */
    ResponseResult getAllLink();
}

