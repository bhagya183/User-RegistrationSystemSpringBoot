package com.bhagyashri.codebproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CodeBProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeBProjectApplication.class, args);
	}

}
