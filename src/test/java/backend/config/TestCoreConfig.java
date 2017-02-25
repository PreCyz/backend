package backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import backend.itest.TestSessionService;
import backend.service.SessionService;

@Configuration
@EnableTransactionManagement
@Import({
	JpaConfig.class, 
	AopConfig.class, 
	ServiceConfig.class, 
	PersistenceContext.class, 
	TestDatabaseConfig.class,
	RepositoryConfig.class})
public class TestCoreConfig {
	
/* <bean id="loggingAppender" class="backend.common.log4j.TestLoggingAppender" /> */
	
	@Bean
	public SessionService sessionService() {
		return new TestSessionService();
	}

}
