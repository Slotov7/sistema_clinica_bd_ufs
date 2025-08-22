package sistema_clinica;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(org.springframework.core.env.Environment env) {
		return args -> {
			System.out.println("--- VERIFICANDO PROPRIEDADE DO SCALAR ---");
			String scalarProperty = env.getProperty("springdoc.swagger-ui.use-scalar-layout");
			System.out.println("O valor lido de 'springdoc.swagger-ui.use-scalar-layout' é: [" + scalarProperty + "]");
			System.out.println("-------------------------------------------");
		};
	}
}
