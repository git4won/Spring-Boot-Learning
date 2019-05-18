package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// 通过@ Configuration 注解，让 Spring 来加载该类配置。再通过 @EnableSwagger2 注解来启用 Swagger2。
@Configuration
@EnableSwagger2
public class Swagger2 {

    // 通过 createRestApi 函数创建 Docket 的 Bean 之后，apiInfo() 用来创建该 Api 的基本信息（这些基本信息会展现在文档页面中）。
    // select() 函数返回一个 ApiSelectorBuilder 实例用来控制哪些接口暴露给 Swagger 来展现，本例采用指定扫描的包路径来定义，
    // Swagger 会扫描该包下所有 Controller 定义的 API，并产生文档内容（除了被 @ApiIgnore 指定的请求）。

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("XXX 接口文档")
                .description("Swagger2 构建的 RESTful APIs")
                .termsOfServiceUrl("https://google.com")
                .contact("NoBody")
                .version("1.0")
                .build();
    }
}
