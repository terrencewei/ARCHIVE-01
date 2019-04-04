package com.terrencewei.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        // home page, default login success redirect page
        registry.addViewController("/").setViewName("homePage");
        // login page
        registry.addViewController("/loginPage").setViewName("loginPage");
        // logged in user page
        registry.addViewController("/loginSuccessPage").setViewName("loginSuccessPage");
    }

}