package backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import backend.service.SessionService;
import backend.service.impl.AuthenticationService;
import backend.service.impl.ExceptionThrowerService;
import backend.service.impl.LogService;
import backend.service.impl.SessionServiceImpl;

@Configuration
public class ServiceConfig {
	
	@Autowired
	private RepositoryConfig repositoryConfig;
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public ExceptionThrowerService exceptionThrowerService() {
		return new ExceptionThrowerService();
	}
	
	@Bean
	public SessionService sessionService() {
		return new SessionServiceImpl();
	}
	
	@Bean
	public LogService logService() {
		LogService logService = new LogService(repositoryConfig.jpaRepository());
		logService.setSessionService(sessionService());
		return logService;
	}
	
	@Bean
	public AuthenticationService authenticationService() {
		AuthenticationService service = new AuthenticationService(exceptionThrowerService());
		service.setAuthenticationDao(repositoryConfig.authenticationDao());
		service.setSessionService(sessionService());
		return service;
	}
	
}
