package backend.log4j;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import backend.config.CoreConfig;
import backend.service.SessionService;
import backend.service.impl.LogService;

public class LoggingAppender extends AppenderSkeleton implements Appender {

	public static final String LOG_LOGIN_ID_SESSION_KEY = "LOG_LOGIN_ID_SESSION_KEY";
	
	@Autowired
	private LogService logService;
	@Autowired
	private SessionService sessionService;
	
	private ApplicationContext context;

	public void refreshSpringBeans() {
		context = new AnnotationConfigApplicationContext(CoreConfig.class);
		logService = context.getBean(LogService.class);
		sessionService = context.getBean(SessionService.class);
	}
	
	@Override
	protected void append(LoggingEvent arg0) {
		
	}

	public void close() {
	}

	public boolean requiresLayout() {
		return false;
	}


}
