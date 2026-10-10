package pl.pollub.cleancode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CleanCodeApiApplication {

	public static void main(String[] args) {
		// Keep the local H2 shared database reachable through loopback.
		if (System.getProperty("h2.bindAddress") == null) {
			System.setProperty("h2.bindAddress", "127.0.0.1");
		}
		SpringApplication.run(CleanCodeApiApplication.class, args);
	}

}
