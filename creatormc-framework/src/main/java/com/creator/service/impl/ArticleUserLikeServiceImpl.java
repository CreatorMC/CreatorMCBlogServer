package com.creator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.dao.ArticleUserLikeDao;
import com.creator.domain.entity.ArticleUserLike;
import com.creator.service.ArticleUserLikeService;
import org.springframework.stereotype.Service;

/**
 * (ArticleUserLike)表服务实现类
 *
 * @author makejava
 * @since 2023-09-24 21:26:09
 */
@Service("articleUserLikeService")
public class ArticleUserLikeServiceImpl extends ServiceImpl<ArticleUserLikeDao, ArticleUserLike> implements ArticleUserLikeService {

}