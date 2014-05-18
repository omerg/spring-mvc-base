package tr.com.lucidcode.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("tr.com.lucidcode")
public class MvcConfiguration extends WebMvcConfigurerAdapter {
	
	protected static Logger logger = Logger.getLogger("sessionListener");

	@Bean
	public ViewResolver getViewResolver(ResourceLoader resourceLoader) {
		
		InternalResourceViewResolver InternalResolver = new InternalResourceViewResolver();
		InternalResolver.setPrefix("/html/");
		InternalResolver.setSuffix(".html");
		InternalResolver.setCache(false);
		InternalResolver.setContentType("text/html;charset=ISO-8859-1");

		return InternalResolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("/");
	}

}
