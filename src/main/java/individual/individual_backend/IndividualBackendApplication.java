package individual.individual_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableWebSocket
@SpringBootApplication
public class IndividualBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndividualBackendApplication.class, args);
	}

}
