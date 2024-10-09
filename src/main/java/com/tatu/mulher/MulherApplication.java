package com.tatu.mulher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RequestMapping(value = "/api")
public class MulherApplication {

	public static void main(String[] args) {
		SpringApplication.run(MulherApplication.class, args);
	}

}
