package se77.spring5web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * Spring-Boot starter class.
 * @author superernie77
 *
 */
@SpringBootApplication
@EnableWebFlux
public class Spring5WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring5WebApplication.class, args);
	}

}
