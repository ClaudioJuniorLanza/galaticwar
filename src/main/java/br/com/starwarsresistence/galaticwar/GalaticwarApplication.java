package br.com.starwarsresistence.galaticwar;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Rebeldes API", version = "1.0", description = "Api para cadastro e troca de recursos entre rebeldes"))
public class GalaticwarApplication {

	public static void main(String[] args) {SpringApplication.run(GalaticwarApplication.class, args);}

}
