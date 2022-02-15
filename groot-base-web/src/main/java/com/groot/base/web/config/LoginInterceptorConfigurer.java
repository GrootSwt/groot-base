package com.groot.base.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnProperty(prefix = "groot",name = "login-interceptor",havingValue = "true")
public class LoginInterceptorConfigurer implements WebMvcConfigurer {

    public LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginInterceptor())
                .excludePathPatterns("/login", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
