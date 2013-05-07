package com.obss.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.obss.service.AccountService;
import com.obss.service.GedikStockService;

@Configuration
@ComponentScan("com.obss.service")
public class ServiceConfiguration {
	
	@Bean
    public AccountService accountService() {
        return new AccountService();
    }
	
	@Bean
    public GedikStockService gedikStockService() {
        return new GedikStockService();
    }
}
