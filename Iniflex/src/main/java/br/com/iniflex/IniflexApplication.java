package br.com.iniflex;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Teste Prático - Iniflex ",
				version = "1.0",
				description = "",
				contact = @Contact(name = "Carlos Roberto", email = "crrsj1@gmail.com")
		)
)
public class IniflexApplication {

	public static void main(String[] args) {
		SpringApplication.run(IniflexApplication.class, args);
	}

}
