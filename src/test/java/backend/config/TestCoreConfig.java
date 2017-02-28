package backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import backend.itest.TestSessionService;
import backend.service.SessionService;

@Configuration
@Import({
	AopConfig.class,
	CxfConfig.class,
	JpaConfig.class,
	RepositoryConfig.class,
	ServiceConfig.class, 
	TestDatabaseConfig.class})
public class TestCoreConfig {
	
	@Bean
	public SessionService sessionService() {
		return new TestSessionService();
	}

}
