package com.tatu.amparo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "amparo", version = "1", description = "Amparo backend"))
public class AmparoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmparoApplication.class, args);
	}

}
