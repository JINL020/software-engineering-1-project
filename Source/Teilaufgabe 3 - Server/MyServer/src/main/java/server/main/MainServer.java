package server.main;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan
@Configuration
public class MainServer {

	private static final int DEFAULT_PORT = 18235;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MainServer.class);

		app.setDefaultProperties(Collections.singletonMap("server.port", DEFAULT_PORT));
		app.run(args);
	}
}
