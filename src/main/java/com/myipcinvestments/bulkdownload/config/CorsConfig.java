package com.myipcinvestments.bulkdownload.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private final ApiConfig apiConfig;

    public CorsConfig(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(apiConfig.getUrl())
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}