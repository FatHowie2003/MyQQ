package com.example.my_qq_spring.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConf implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")  // 使用 allowedOriginPatterns 替代 allowedOrigins
                .allowedMethods("POST", "GET", "OPTIONS", "PUT", "PATCH")
                .maxAge(168000)
                .allowedHeaders("*")
                .allowCredentials(true);  // 允许凭证
        System.out.println("读取了CORS配置类!!!");
    }
}

