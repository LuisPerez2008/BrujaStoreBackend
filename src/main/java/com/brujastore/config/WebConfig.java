package com.brujastore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // registry.addMapping("/api/**") // Aplica CORS a todos los endpoints bajo /api
                //     .allowedOrigins("http://localhost:4200", "http://localhost:3000", "http://localhost:5173")
                //     .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                //     .allowedHeaders("*") // Todos los headers permitidos
                //     .allowCredentials(true); // Permite el envío de cookies y credenciales

                // Configuración más permisiva para desarrollo (permite cualquier origen)
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}
