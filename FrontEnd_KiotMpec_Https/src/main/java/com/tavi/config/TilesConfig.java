package com.tavi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@Configuration
public class TilesConfig implements WebMvcConfigurer {

    @Bean(name = "viewResolver")
    public ViewResolver getViewResolver() {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();

        // TilesView 3
        viewResolver.setViewClass(TilesView.class);

        return viewResolver;
    }

    @Bean(name = "tilesConfigurer")
    public TilesConfigurer getTilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();

        // TilesView 3
        tilesConfigurer.setDefinitions("/WEB-INF/tiles.xml");

        return tilesConfigurer;
    }
    
    @Override //cau hinh duong dan tinh
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/resources/bootstrap/**")
                .addResourceLocations("classpath:/static/bootstrap/");
        registry.addResourceHandler("/resources/build/**")
                .addResourceLocations("classpath:/static/build/");
        registry.addResourceHandler("/resources/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/resources/dist/**")
                .addResourceLocations("classpath:/static/dist/");
        registry.addResourceHandler("/resources/pages/**")
                .addResourceLocations("classpath:/static/dist/js/ajax/pages/");
        registry.addResourceHandler("/resources/model/**")
                .addResourceLocations("classpath:/static/dist/js/ajax/model/");
        registry.addResourceHandler("/resources/plugins/**")
                .addResourceLocations("classpath:/static/plugins/");
        registry.addResourceHandler("/resources/vendor/**")
                .addResourceLocations("classpath:/static/vendor/");
        registry.addResourceHandler("/resources/login/**")
                .addResourceLocations("classpath:/static/login/");
    }
}
