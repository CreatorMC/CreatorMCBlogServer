package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.constants.SystemConstants;
import com.creator.dao.ImageRandomDao;
import com.creator.domain.entity.ImageRandom;
import com.creator.service.ImageRandomService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (ImageRandom)表服务实现类
 *
 * @author makejava
 * @since 2023-08-19 23:38:46
 */
@Service("imageRandomService")
public class ImageRandomServiceImpl extends ServiceImpl<ImageRandomDao, ImageRandom> implements ImageRandomService {

    @Override
    public List<ImageRandom> listShowImg(String type) {
        return list(new LambdaQueryWrapper<ImageRandom>()
                .eq(ImageRandom::getIsShow, SystemConstants.IMAGE_RANDOM_SHOW)
                .eq(ImageRandom::getType, type)
        );
    }
}

