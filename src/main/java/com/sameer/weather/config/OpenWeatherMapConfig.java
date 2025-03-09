package com.sameer.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class OpenWeatherMapConfig {

	@Bean WebClient.Builder getWebBuilder()
	{
		return WebClient.builder();
	}
	
	@Bean ObjectMapper objectMapper()
	{
		return new ObjectMapper();
	}
}
