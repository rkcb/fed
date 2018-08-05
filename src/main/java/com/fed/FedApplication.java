package com.fed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.fed.repositories")
public class FedApplication {

	public static void main(String[] args) {
		SpringApplication.run(FedApplication.class, args);
	}
}
