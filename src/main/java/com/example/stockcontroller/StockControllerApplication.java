package com.example.stockcontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class StockControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockControllerApplication.class, args);
	}

	@RestController
	public class TestController {

		@GetMapping("/test")
		public String testEndpoint() {
			return "¡La API está funcionando!";

		}
	}

}