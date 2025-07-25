package com.example.discoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryserverApplication.class, args);
	}

}
