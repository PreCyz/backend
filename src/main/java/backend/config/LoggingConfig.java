package backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import backend.log4j.LoggingAppender;
import backend.service.SessionService;
import backend.service.impl.LogService;

@Configuration
public class LoggingConfig {
	
	@Autowired
	private LogService logService;
	
	@Bean
	public LoggingAppender loggingAppender(ApplicationContext applicationContext) {
		LoggingAppender loggingAppender = new LoggingAppender();
		loggingAppender.setApplicationContext(applicationContext);
		loggingAppender.setLogService(logService);
		return loggingAppender;
	}

}
