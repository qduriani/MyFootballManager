package com.myfootballmanager.playerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PlayerserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlayerserviceApplication.class, args);
	}

}
