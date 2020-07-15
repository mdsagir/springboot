package com.green.springbootjwt.config;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class WebConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public Docket swaggerConfiguration() {
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .paths(PathSelectors.ant("/api/*"))
//                .apis(RequestHandlerSelectors.basePackage("com.green.springbootjwt"))
//                .build()
//                .apiInfo(apiDetails());
//    }
//
//    private ApiInfo apiDetails() {
//        return new ApiInfo(
//                "Contact Book API",
//                "Sample API for description",
//                "1.0",
//                "Free to use",
//                new springfox.documentation.service.Contact("geen java", "greenjava.com", "abc@.com"),
//                "API Licence",
//                "greenjava.com",
//                Collections.emptyList());
//
//    }
}
