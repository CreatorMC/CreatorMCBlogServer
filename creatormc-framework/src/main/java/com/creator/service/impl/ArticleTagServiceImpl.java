package com.creator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.dao.ArticleTagDao;
import com.creator.domain.entity.ArticleTag;
import com.creator.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-08-11 10:08:28
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagDao, ArticleTag> implements ArticleTagService {

}

