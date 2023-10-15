package com.myfootballmanager.playerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PlayerserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlayerserviceApplication.class, args);
	}

}
