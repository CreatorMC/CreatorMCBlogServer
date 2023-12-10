package com.creator;

import com.creator.constants.SystemConstants;
import com.creator.domain.entity.SensitiveWord;
import com.creator.service.SensitiveWordService;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@SpringBootTest
public class SensitiveWordTest {

    @Autowired
    SensitiveWordBs sensitiveWordBs;

    @Autowired
    SensitiveWordService sensitiveWordService;

    @Test
    public void testWord() {
        System.out.println(sensitiveWordBs.replace("我是中国人，我的敏感词。"));
    }

    /**
     * 从资源文件中初始化数据库中敏感词
     */
    @Test
    public void initMySQL() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sensitive_word.txt");
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        sensitiveWordService.save(new SensitiveWord(null, line, SystemConstants.SENSITIVE_WORD_TYPE_DENY,
                                1L, null, 1L, null, 0));
                    } catch (Exception e) {

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
