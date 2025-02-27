package pl.wylegala.ideas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication(exclude = {com.vaadin.flow.spring.SpringBootAutoConfiguration.class})
@RestController

public class IdeasApplication {


	public IdeasApplication() {
	}

	@GetMapping("/test")
	public String test() {
		return "" + System.currentTimeMillis();
	}

	public static void
	main(String[] args) {
		SpringApplication.run(IdeasApplication.class, args);
	}

}
