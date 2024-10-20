package com.tatu.amparo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RequestMapping(value = "/api")
public class AmparoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmparoApplication.class, args);
	}

}
