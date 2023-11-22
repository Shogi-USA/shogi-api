package com.shogiusa.shogiapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main application class for the Shogi API Spring Boot application.
 * This class contains the main method to run the Spring Boot application
 * and optional CommandLineRunner bean for initial setup or testing purposes.
 */
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ShogiApiApplication {

	/**
	 * Main method to launch the Spring Boot application.
	 *
	 * @param args Command line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ShogiApiApplication.class, args);
	}

	/**
	 * Optional CommandLineRunner bean to automatically create admin and manager users
	 * for testing purposes during application startup.
	 *
	 * <p>Note: This should only be enabled in the testing phase to prevent
	 * the automatic creation of admin and manager users on every application run.
	 * The role field in UserCreationRequest should not be exposed to public APIs
	 * to prevent unauthorized creation of admin and manager roles.</p>
	 *
	 * @param service AuthenticationService to handle user registration.
	 * @return CommandLineRunner
	 */
	// Uncomment the following method for testing purposes
//	@Bean
//	public CommandLineRunner commandLineRunner(
//			AuthenticationService service
//	) {
//		return args -> {
//			var admin = UserCreationRequest.builder()
//					.username("admin")
//					.firstName("Admin")
//					.lastName("Admin")
//					.email("admin@mail.com")
//					.displayName("Admin")
//					.password("password")
//					.role(ADMIN)
//					.build();
//			System.out.println("Admin token: " + service.register(admin).getAccessToken());
//
//			var manager = UserCreationRequest.builder()
//					.firstName("Admin")
//					.lastName("Admin")
//					.email("manager@mail.com")
//					.displayName("Admin")
//					.password("password")
//					.role(MANAGER)
//					.build();
//			System.out.println("Manager token: " + service.register(manager).getAccessToken());
//
//		};
//	}
}