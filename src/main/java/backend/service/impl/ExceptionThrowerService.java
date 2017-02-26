package backend.service.impl;

import org.apache.commons.logging.Log;

import backend.exception.ApplicationUncheckedException;
import backend.helper.StringHelper;

public class ExceptionThrowerService {
	
	private static final String EXCEPTION_MESSAGE_PREFIX = "backend.service.empty.parameter";
	
	private Log logger;
	private String serviceName = "SERVICE NAME NOT SET!!!";
	
	public void setLogger(Log logger) {
		this.logger = logger;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public <A> void throwNullOrEmpty(A value, String paramName) {
		if (value == null) {
			logAndThrowException(paramName);
		}
		if (value instanceof String) {
			if (StringHelper.empty( (String)value )) {
				logAndThrowException(paramName);
			}
		}
	}
	
	private void logAndThrowException(String paramName) {
		String errorInfo = String.format("%s - empty parameter [%s].", serviceName, paramName);
		logger.error(errorInfo);
		throw new ApplicationUncheckedException(errorInfo, EXCEPTION_MESSAGE_PREFIX, new String[]{serviceName, paramName});
	}
}
