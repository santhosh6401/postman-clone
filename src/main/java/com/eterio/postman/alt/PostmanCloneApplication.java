package com.eterio.postman.alt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.eterio.postman.alt")
public class PostmanCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostmanCloneApplication.class, args);
	}

}
