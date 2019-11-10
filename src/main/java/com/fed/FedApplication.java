package com.fed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EntityScan(
//		basePackageClasses = { FedApplication.class, Jsr310JpaConverters.class }
//)
public class FedApplication {
	public static void main(String[] args) {
		SpringApplication.run(FedApplication.class, args);
	}
}
