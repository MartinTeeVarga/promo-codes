package com.czequered.promocodes.config;

import com.czequered.promocodes.controller.LoginControllerDev;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Martin Varga
 */
@Configuration
public class WebMvcConfig {

    Logger logger = LoggerFactory.getLogger(LoginControllerDev.class);

    @Value("${jepice.frontend.url:none}")
    private String corsUrl;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                logger.info("Configuring CORS for {}", corsUrl);
                if (!"none".equals(corsUrl)) {
                    registry.addMapping("/**").allowedOrigins(corsUrl).allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS");
                }
            }
        };
    }
}
