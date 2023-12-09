package com.creator;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SensitiveWordTest {

    @Autowired
    SensitiveWordBs sensitiveWordBs;

    @Test
    public void testWord() {
        System.out.println(sensitiveWordBs.replace("我是中国人，我的敏感词。"));
    }
}
