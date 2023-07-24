package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.constants.SystemConstants;
import com.creator.dao.LinkDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.entity.Link;
import com.creator.domain.vo.LinkVo;
import com.creator.service.LinkService;
import com.creator.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-07-24 16:03:35
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkDao, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        List<Link> links = list(new LambdaQueryWrapper<Link>().eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL));
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }
}

