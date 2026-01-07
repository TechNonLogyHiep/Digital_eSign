package com.project.digital_esign.digital_eSign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class DigitalESignApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalESignApplication.class, args);
	}

}
