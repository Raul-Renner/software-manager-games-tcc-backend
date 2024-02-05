package com.br;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.ALL;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;
import static com.fasterxml.jackson.databind.DeserializationFeature.*;
import static com.fasterxml.jackson.databind.SerializationFeature.*;

@SpringBootApplication
public class TaskGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskGameApplication.class, args);
	}

	@Bean
	@Primary
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		return new ObjectMapper()
				.setVisibility(ALL, NONE)
				.setVisibility(FIELD, ANY)
				.setSerializationInclusion(NON_EMPTY)
				.enable(INDENT_OUTPUT)
				.enable(USE_EQUALITY_FOR_OBJECT_ID)
				.enable(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
				.disable(WRITE_DATES_AS_TIMESTAMPS)
				.disable(ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.disable(FAIL_ON_UNKNOWN_PROPERTIES);
	}

	@Bean
	@Primary
	public RestTemplate restTemplate() { return new RestTemplate(); }
}
