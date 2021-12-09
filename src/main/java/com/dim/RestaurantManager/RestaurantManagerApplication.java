package com.dim.RestaurantManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RestaurantManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantManagerApplication.class, args);
	}
}
