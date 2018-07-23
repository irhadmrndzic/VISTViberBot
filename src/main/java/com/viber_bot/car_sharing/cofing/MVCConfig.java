package com.viber_bot.car_sharing.cofing;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements  WebMvcConfigurer{
    public void addViewControllers(ViewControllerRegistry registry) {
       // registry.addViewController("/home").setViewName("home");

        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/routes").setViewName("Routes");
    }
}
