package com.skillvibe.tutoring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Esto le da permiso a tu Frontend de hablar con el Backend
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5173", // Para cuando pruebes en tu PC
                        "https://skillvibe-frontend.vercel.app" // O la URL que te dé Vercel
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}