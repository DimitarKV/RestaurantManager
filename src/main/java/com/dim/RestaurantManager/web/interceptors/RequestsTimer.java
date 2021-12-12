package com.dim.RestaurantManager.web.interceptors;

import com.dim.RestaurantManager.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RequestsTimer {
    private final StatusRepository statusRepository;
    private final Logger logger = LoggerFactory.getLogger(RequestsTimer.class);

    public RequestsTimer(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Scheduled(fixedDelay = 1000)
    public void checkRestCallsForLastSecond() {
        Long calls = statusRepository.getRestCallsLastSecond();
        statusRepository.setRestCallsLastSecond(0L);

        if(calls > 5)
            logger.warn("There has been " + calls + " rest calls for the last second!");
    }
}
