package com.cashier.web.config;

import javax.validation.Validation;
import javax.validation.Validator;

import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

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
	
	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
	    CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
	    loggingFilter.setIncludeClientInfo(true);
	    loggingFilter.setIncludeQueryString(true);
	    loggingFilter.setIncludePayload(true);
	    return loggingFilter;
	}
	
	@Bean
	public FilterRegistrationBean<CommonsRequestLoggingFilter> loggingFilterRegistration() {
	    FilterRegistrationBean<CommonsRequestLoggingFilter> registration = 
	    		new FilterRegistrationBean<CommonsRequestLoggingFilter>(requestLoggingFilter());
	    registration.addUrlPatterns("/register");
	    return registration;
	}
}
