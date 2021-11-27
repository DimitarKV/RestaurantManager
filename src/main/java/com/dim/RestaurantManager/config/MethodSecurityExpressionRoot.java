package com.dim.RestaurantManager.config;

import com.dim.RestaurantManager.service.UserService;
import com.dim.RestaurantManager.service.impl.RestaurantUser;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class MethodSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {
    private Object filterObject, returnObject;
    private UserService userService;

    //TODO
    public boolean isAdmin(){
        return false;
    }

    private RestaurantUser currentUser(){
        Authentication authentication = getAuthentication();
        if(authentication.getPrincipal() instanceof UserDetails)
            return ((RestaurantUser)authentication.getPrincipal());
        return null;
    }

    /**
     * Creates a new instance
     *
     * @param authentication the {@link Authentication} to use. Cannot be null.
     */
    public MethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    public MethodSecurityExpressionRoot setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }
}
