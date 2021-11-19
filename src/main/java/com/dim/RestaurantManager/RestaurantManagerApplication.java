package com.dim.RestaurantManager;

import com.dim.RestaurantManager.utils.QRGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestaurantManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantManagerApplication.class, args);
	}

	@Bean
	public QRGenerator generator(){
		return new QRGenerator();
	}
}
