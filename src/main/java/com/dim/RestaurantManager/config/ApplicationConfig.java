package com.dim.RestaurantManager.config;

import com.dim.RestaurantManager.repository.impl.StatusRepositoryImpl;
import com.dim.RestaurantManager.repository.StatusRepository;
import com.dim.RestaurantManager.utils.components.ClassMapper;
import com.dim.RestaurantManager.utils.components.impl.ClassMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class ApplicationConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    @Bean
    public ClassMapper classMapper() {
        return new ClassMapperImpl();
    }

    @Bean
    public StatusRepository statusRepository() {
        return new StatusRepositoryImpl();
    }
}
