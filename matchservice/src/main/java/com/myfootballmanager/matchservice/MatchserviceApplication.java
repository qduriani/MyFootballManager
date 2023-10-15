package com.myfootballmanager.matchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MatchserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchserviceApplication.class, args);
	}

}
