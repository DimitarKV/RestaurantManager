package com.dim.RestaurantManager.repository;

import com.dim.RestaurantManager.repository.impl.StatusRepositoryImpl;

public interface StatusRepository {

    StatusRepositoryImpl newRestCall();

    Long getRestCallsLastSecond();

    StatusRepositoryImpl setRestCallsLastSecond(Long restCallsLastSecond);
}
