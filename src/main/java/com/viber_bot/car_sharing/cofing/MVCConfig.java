package com.viber_bot.car_sharing.cofing;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("Login");
        registry.addViewController("/login").setViewName("Login");


        registry.addViewController("/index").setViewName("Index");
        registry.addViewController("/routes").setViewName("Routes");
        registry.addViewController("/users").setViewName("User/Users");

    }
}
