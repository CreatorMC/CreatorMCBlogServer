package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.domain.entity.ImageRandom;

import java.util.List;

/**
 * (ImageRandom)表服务接口
 *
 * @author makejava
 * @since 2023-08-19 23:38:46
 */
public interface ImageRandomService extends IService<ImageRandom> {

    /**
     * 查询所有参与随机显示的并指定类型的图片
     * @param type 图片类型
     * @return
     */
    List<ImageRandom> listShowImg(String type);
}

