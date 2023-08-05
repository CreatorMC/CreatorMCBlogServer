package com.creator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//开启定时任务
@EnableScheduling
public class CreatorMCBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreatorMCBlogApplication.class);
    }
}
