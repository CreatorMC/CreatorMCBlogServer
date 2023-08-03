package com.creator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.creator.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("创造者MC", "https://github.com/CreatorMC/CreatorMCBlog", "邮箱");
        return new ApiInfoBuilder()
                .title("标题")
                .description("描述")
                .contact(contact)
                .version("1.0.0")
                .build();
    }
}
