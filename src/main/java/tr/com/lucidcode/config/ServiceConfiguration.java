package tr.com.lucidcode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import tr.com.lucidcode.service.AccountService;

@Configuration
@ComponentScan("tr.com.lucidcode.service")
public class ServiceConfiguration {
	
	@Bean
    public AccountService accountService() {
        return new AccountService();
    }
}
