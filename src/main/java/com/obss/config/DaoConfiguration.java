package com.obss.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.obss.dao.AccountDao;

@Configuration
@ComponentScan("com.obss.dao")
public class DaoConfiguration {
	
	@Bean
    public AccountDao accountDao() {
        return new AccountDao();
    }

}
