package backend.service;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.LogFactory;

import backend.service.impl.ExceptionThrowerService;

public abstract class AbstractService {
	
	protected ExceptionThrowerService exceptionThrower;
	
	public AbstractService(ExceptionThrowerService exceptionThrower) {
		this.exceptionThrower = exceptionThrower;
	}
	
	@PostConstruct
	public void initExceptionThrower(){
		Class<? extends AbstractService> clazz = this.getClass();
		exceptionThrower.setLogger(LogFactory.getLog(clazz));
		exceptionThrower.setServiceName(clazz.getSimpleName());
	}
	
}
