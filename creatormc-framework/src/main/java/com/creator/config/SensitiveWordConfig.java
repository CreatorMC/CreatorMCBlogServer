package com.creator.config;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class SensitiveWordConfig {
    @Autowired
    private MyWordDeny myWordDeny;

    @Autowired
    private MyWordAllow myWordAllow;

    @Bean
    public SensitiveWordBs sensitiveWordBs() {
        SensitiveWordBs sensitiveWordBs = SensitiveWordBs.newInstance()
                .wordAllow(myWordAllow)
                .wordDeny(myWordDeny)
                //其他各种配置
                .init();
        log.info("SensitiveWordBs 敏感词数据结构初始化完成");
        return sensitiveWordBs;
    }
}
