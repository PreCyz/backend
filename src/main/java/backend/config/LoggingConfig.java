package backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import backend.log4j.LoggingAppender;

@Configuration
public class LoggingConfig {
	
	@Bean
	public LoggingAppender loggingAppender() {
		return new LoggingAppender();
	}

}
