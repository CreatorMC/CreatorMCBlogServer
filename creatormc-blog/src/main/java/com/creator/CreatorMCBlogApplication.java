package com.creator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//开启定时任务
@EnableScheduling
//开启Swagger2
@EnableSwagger2
public class CreatorMCBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreatorMCBlogApplication.class);
    }
}
