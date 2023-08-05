package com.creator.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//开启Swagger2
@ConditionalOnProperty(prefix = "swagger", value = {"enable"}, havingValue = "true")
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //要扫描的接口的包名
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
