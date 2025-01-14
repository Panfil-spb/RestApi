package org.example.restapi.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user").setViewName("user");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Для всех маршрутов
                .allowedOrigins("http://localhost:8080") // Или домен фронтенда
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Доступные методы
                .allowedHeaders("*"); // Заголовки
    }

}
