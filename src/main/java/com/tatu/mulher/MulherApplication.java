package com.tatu.mulher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MulherApplication {

	public static void main(String[] args) {
		SpringApplication.run(MulherApplication.class, args);
	}

	@GetMapping("hello")
	static public String hello(@RequestParam(value="text", defaultValue="Nenhum valor inserido") String text){
		System.out.println(text);
		return String.format("The params passed to the API: " + text);
	}
}
