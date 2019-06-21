package com.cashier.web.config;

import javax.validation.Validation;
import javax.validation.Validator;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan(value={
		"com.cashier.repository",
		"com.cashier.service",
		"com.cashier.web"})
@PropertySources({ 
	@PropertySource("classpath:config-cashier.properties"),
	@PropertySource(value = "file:config-cashier.properties", ignoreResourceNotFound = true	)
})
public class ApplicationConfig {

	@Bean
	public Validator getValidator() {
		return Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	
}
