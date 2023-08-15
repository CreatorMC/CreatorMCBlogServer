package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.constants.SystemConstants;
import com.creator.dao.LinkDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.GetPageLinkListDto;
import com.creator.domain.entity.Link;
import com.creator.domain.vo.GetPageLinkListVo;
import com.creator.domain.vo.LinkVo;
import com.creator.domain.vo.PageVo;
import com.creator.service.LinkService;
import com.creator.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-07-24 16:03:35
 */
@SuppressWarnings("rawtypes")
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkDao, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        List<Link> links = list(new LambdaQueryWrapper<Link>().eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL));
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult getPageLinkList(Integer pageNum, Integer pageSize, GetPageLinkListDto dto) {
        //分页查询
        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page, new LambdaQueryWrapper<Link>()
                //根据友链名称进行模糊查询
                .like(StringUtils.hasText(dto.getName()), Link::getName, dto.getName())
                //根据状态进行查询
                .eq(StringUtils.hasText(dto.getStatus()), Link::getStatus, dto.getStatus())
        );
        //转换
        List<GetPageLinkListVo> vos = BeanCopyUtils.copyBeanList(page.getRecords(), GetPageLinkListVo.class);
        //封装
        return ResponseResult.okResult(new PageVo(vos, page.getTotal()));
    }

    @Override
    public ResponseResult addLink(Link link) {
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLink(Long id) {
        Link link = getById(id);
        GetPageLinkListVo vo = BeanCopyUtils.copyBean(link, GetPageLinkListVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateLink(Link link) {
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(List<Long> id) {
        removeByIds(id);
        return ResponseResult.okResult();
    }
}

