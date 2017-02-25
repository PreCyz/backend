package backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import backend.itest.TestSessionService;
import backend.service.SessionService;

@Configuration
@Import({
	AopConfig.class, 
	ServiceConfig.class, 
	JpaConfig.class, 
	TestDatabaseConfig.class,
	RepositoryConfig.class})
public class TestCoreConfig {
	
/* <bean id="loggingAppender" class="backend.common.log4j.TestLoggingAppender" /> */
	
	@Bean
	public SessionService sessionService() {
		return new TestSessionService();
	}

}
