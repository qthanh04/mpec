package com.tavi.tavi_mrs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "com.tavi.tavi_mrs.config")
public class MvcConfig implements WebMvcConfigurer {

    // cho phep domain khac truy cap api voi tat ca cac method
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
            }
        };
    }

    @Override // cau hinh duong dan tinh
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/resources/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/resources/files/**")
                .addResourceLocations("classpath:/static/files/");
        registry.addResourceHandler("/resources/file_upload/**")
                .addResourceLocations("classpath:/file_upload/");
        registry.addResourceHandler("/resources/tool/**")
                .addResourceLocations("classpath:/static/tools/");
        registry.addResourceHandler("/resources/img/**")
                .addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
