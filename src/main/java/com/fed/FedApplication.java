package com.fed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(
		basePackageClasses = { FedApplication.class, Jsr310JpaConverters.class }
)
public class FedApplication {

	public static void main(String[] args) {
		SpringApplication.run(FedApplication.class, args);
	}
}
