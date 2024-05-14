package com.br;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;

@SpringBootApplication
public class TaskGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskGameApplication.class, args);
	}

	@Bean
	public JavaMailSender javaMailSender() {
		return new JavaMailSenderImpl();
	}

	@Bean
	public TemplateEngine templateEngine() {
		return new TemplateEngine();
	}
}