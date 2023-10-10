package com.creator.utils;

import java.util.Random;

public class RandomUtils {
    /**
     * 生成验证码
     * @return
     */
    public static String generateVCode() {
        StringBuilder codeNum = new StringBuilder();
        int [] code = new int[3];
        Random random = new Random();
        //自动生成验证码
        for (int i = 0; i < 6; i++) {
            int num = random.nextInt(10) + 48;
            int uppercase = random.nextInt(26) + 65;
            int lowercase = random.nextInt(26) + 97;
            code[0] = num;
            code[1] = uppercase;
            code[2] = lowercase;
            codeNum.append((char) code[random.nextInt(3)]);
        }
        return codeNum.toString();
    }
}
