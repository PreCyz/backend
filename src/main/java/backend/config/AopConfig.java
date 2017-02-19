package backend.config;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import backend.annotation.SecurityGuard;
import backend.aspect.ActiveSessionAspect;
import backend.aspect.ContractAccessAspect;
import backend.service.SessionService;

@Aspect
public class AopConfig {
	
	@Autowired
	private SessionService sessionService;
	
	@Pointcut("within(backend.service.impl.*) and @annotation(securityGuard)")
	public void allServices(SecurityGuard activeSession) {}
	
	@Pointcut("within(backend.service.impl.*) and args(contractNumber,..) and @annotation(securityGuard)")
	public void servicesWithContract(SecurityGuard securityGuard, String contractNumber) {}
	
	@Bean
	public ActiveSessionAspect activeSessionAspect() {
		return new ActiveSessionAspect(sessionService);
	}
	
	@Bean
	public ContractAccessAspect contractAccessAspect() {
		return new ContractAccessAspect(sessionService);
	}
	
	@Before("allServices(securityGuard)")
	public void activeSessionAspect(SecurityGuard activeSession) {
		activeSessionAspect().checkActiveSession(activeSession);
	}
	
	@Before("servicesWithContract(securityGuard, contractNumber)")
	public void contractAccessAspectBefore(SecurityGuard securityGuard, String contractNumber) {
		contractAccessAspect().checkContractAccess(securityGuard, contractNumber);
	}
	
	@After("servicesWithContract(securityGuard, contractNumber)")
	public void contractAccessAspectAfter(SecurityGuard securityGuard, String contractNumber) {
		contractAccessAspect().checkContractAccess(securityGuard, contractNumber);
	}

}
