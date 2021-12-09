package com.dim.RestaurantManager.config;

import com.dim.RestaurantManager.web.interceptors.RestCallsMonitorInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private final RestCallsMonitorInterceptor restCallsMonitorInterceptor;

    public WebConfiguration(RestCallsMonitorInterceptor restCallsMonitorInterceptor) {
        this.restCallsMonitorInterceptor = restCallsMonitorInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(restCallsMonitorInterceptor);
    }
}
