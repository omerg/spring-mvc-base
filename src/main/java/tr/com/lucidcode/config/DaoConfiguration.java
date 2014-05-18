package tr.com.lucidcode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import tr.com.lucidcode.dao.AccountDao;

@Configuration
@ComponentScan("tr.com.lucidcode.dao")
public class DaoConfiguration {
	
	@Bean
    public AccountDao accountDao() {
        return new AccountDao();
    }

}
