package com.dim.RestaurantManager.config;

import com.dim.RestaurantManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends GlobalMethodSecurityConfiguration {
    @Autowired
    private RestaurantMethodSecurityExpressionHandler expressionHandler;



    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return expressionHandler;
    }

    @Bean
    public RestaurantMethodSecurityExpressionHandler expressionHandlerResolver(UserService userService){
        return new RestaurantMethodSecurityExpressionHandler(userService);
    }
}
