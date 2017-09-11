package com.rockingengineering.hazelcast.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class HazelcastApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(HazelcastApplication.class, args);
	}
}