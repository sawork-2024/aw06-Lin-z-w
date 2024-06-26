package com.example.webpos.order.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConf implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
               .allowedOrigins("*")
               .allowedMethods("GET", "POST", "HEAD", "PUT", "DELETE", "OPTIONS", "PATCH")
               .allowedHeaders("*");
//               .allowCredentials(true);
    }
}
