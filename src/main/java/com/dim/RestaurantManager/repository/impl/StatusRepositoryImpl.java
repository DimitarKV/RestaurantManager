package com.dim.RestaurantManager.repository.impl;

import com.dim.RestaurantManager.repository.StatusRepository;

public class StatusRepositoryImpl implements StatusRepository {
    private Long restCallsLastSecond = 0L;

    @Override
    public StatusRepositoryImpl newRestCall() {
        restCallsLastSecond++;
        return this;
    }

    @Override
    public Long getRestCallsLastSecond() {
        return restCallsLastSecond;
    }

    @Override
    public StatusRepositoryImpl setRestCallsLastSecond(Long restCallsLastSecond) {
        this.restCallsLastSecond = restCallsLastSecond;
        return this;
    }
}
