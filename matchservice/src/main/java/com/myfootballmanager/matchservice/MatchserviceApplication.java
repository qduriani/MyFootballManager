package com.myfootballmanager.matchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MatchserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchserviceApplication.class, args);
	}

}
