package backend.log4j;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import backend.config.CoreConfig;
import backend.exception.ApplicationCheckedException;
import backend.jpa.Entry;
import backend.jpa.entity.log.LogEvent;
import backend.jpa.entity.log.LogLogin;
import backend.service.impl.LogService;

public class LoggingAppender extends AppenderSkeleton implements Appender {

	public static final String LOG_LOGIN_ID_SESSION_KEY = "LOG_LOGIN_ID_SESSION_KEY";
	
	private LogService logService;
	
	private ApplicationContext applicationContext;
	
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void refreshSpringBeans() {
		applicationContext = new AnnotationConfigApplicationContext(CoreConfig.class);
		logService = applicationContext.getBean(LogService.class);
	}
	
	@Override
	protected void append(LoggingEvent event) {
		if (logService == null) {
			refreshSpringBeans();
		}
		try {
			Entry entry = getSpecificEntry(event);
			logService.saveLog(entry);
		} catch (ApplicationCheckedException e) {
			//nothing to store
		}
	}
	
	private Entry getSpecificEntry(LoggingEvent loggingEvent) throws ApplicationCheckedException {
		if (loggingEvent == null) {
			throw new ApplicationCheckedException("Logging event is null.");
		}
		if (loggingEvent.getMessage() instanceof LogLogin) {
			return prepareLogLogin(loggingEvent);
		} else if (loggingEvent.getMessage() instanceof LogLogin) {
			return prepareLogEvent(loggingEvent);
		} else {
			throw new ApplicationCheckedException("There is no Entry to store in databse.");
		}
	}
	
	private LogLogin prepareLogLogin(LoggingEvent loggingEvent) {
		LogLogin logLogin = (LogLogin) loggingEvent.getMessage();
		return logLogin;
	}
	
	private LogEvent prepareLogEvent(LoggingEvent loggingEvent) {
		LogEvent logEvent = (LogEvent) loggingEvent.getMessage();
		return logEvent;
	}

	public void close() {
	}

	public boolean requiresLayout() {
		return false;
	}

}
