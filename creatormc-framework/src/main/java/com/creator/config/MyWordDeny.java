package com.creator.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.creator.constants.SystemConstants;
import com.creator.domain.entity.SensitiveWord;
import com.creator.service.SensitiveWordService;
import com.github.houbb.sensitive.word.api.IWordDeny;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义敏感词（黑名单）
 */
@Component
public class MyWordDeny implements IWordDeny {

    @Autowired
    private SensitiveWordService sensitiveWordService;

    /**
     * 从数据库中查询敏感词黑名单
     * @return
     */
    @Override
    public List<String> deny() {
        List<SensitiveWord> list = sensitiveWordService.list(new LambdaQueryWrapper<SensitiveWord>()
                .eq(SensitiveWord::getType, SystemConstants.SENSITIVE_WORD_TYPE_DENY)
        );
        return list.stream().map(SensitiveWord::getContent).collect(Collectors.toList());
    }
}
