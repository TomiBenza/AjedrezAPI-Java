package TPI.AjedrezApi;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AjedrezApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AjedrezApiApplication.class, args);

	}
	@Bean
	public OpenAPI customOpenApi(){
		return new OpenAPI().info(new Info()
				.title("Ajedrez")
				.version("v.0.1")
		);
	}

}
