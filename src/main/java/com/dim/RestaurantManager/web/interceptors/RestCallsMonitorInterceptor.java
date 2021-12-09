package com.dim.RestaurantManager.web.interceptors;

import com.dim.RestaurantManager.repository.StatusRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RestCallsMonitorInterceptor implements HandlerInterceptor {
    private final StatusRepository statusRepository;

    public RestCallsMonitorInterceptor(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().endsWith("-rest"))
            statusRepository.newRestCall();
        return true;
    }
}
