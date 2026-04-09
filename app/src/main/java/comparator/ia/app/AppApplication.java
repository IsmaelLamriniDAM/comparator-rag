package comparator.ia.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(
		title = "De horas a un solo (click)",
		summary = "Microservices that consume data from different websites to analyze the profitability of buying a car."
		),
		servers = @Server(url = "http://localhost:${server.port}/", description = "Server URL")
)
@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
