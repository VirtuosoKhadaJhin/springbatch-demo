package com.nuanyou.merchant.batch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created by mylon.sun on 2018/1/29
 */
@Configuration
public class SwaggerDocumentationConfig {

    @Value("${swagger.domain}")
    private String swaggerDomain;

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("NUANYOU SCENE CODE BATCH API")
                .description("暖游3.0 推荐码批处理服务")
                .license("")
                .licenseUrl("")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("", "", ""))
                .build();
    }

    @Bean
    public Docket customImplementation() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nuanyou.merchant.batch.controller"))
                .build().apiInfo(apiInfo());
        if (swaggerDomain != null) {
            docket = docket.host(swaggerDomain);
        }
        return docket;
    }
}
