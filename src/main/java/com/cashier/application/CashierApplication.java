package com.cashier.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cashier.web.config.ApplicationConfig;


@SpringBootApplication(scanBasePackageClasses = { ApplicationConfig.class })
public class CashierApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(CashierApplication.class);
	
	public static void main(String[] args) throws InterruptedException {
		LOGGER.info("Starting CASHIER Web Application");
		SpringApplication.run(CashierApplication.class, args);
	}
}
