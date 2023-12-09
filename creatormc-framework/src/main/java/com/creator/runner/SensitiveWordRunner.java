package com.creator.runner;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SensitiveWordRunner implements CommandLineRunner {

    /**
     * 为了触发 SensitiveWordBs 的初始化
     */
    @Autowired
    private SensitiveWordBs sensitiveWordBs;

    @Override
    public void run(String... args) throws Exception {

    }
}
