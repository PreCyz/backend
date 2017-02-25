package backend.itest;

import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import backend.jpa.impl.JpaRepository;
import backend.jpa.newlog.entities.LoggerEvent;
import backend.jpa.newlog.entities.LoggerLogin;
import backend.service.SessionService;

public class CrudJpaIT extends AbstractIT {
	
	private final JpaRepository jpaRepository;
	private final SessionService sessionService; 
	
	public CrudJpaIT(JpaRepository jpaRepository, SessionService sessionService) {
		this.jpaRepository = jpaRepository;
		this.sessionService = sessionService;
	}

	private LoggerLogin createLoggerLogin() {
		LoggerLogin loggerLogin = new LoggerLogin();
		loggerLogin.setAgentName("Mozilla FireFox");
		loggerLogin.setLogDate(Calendar.getInstance());
		loggerLogin.setUserName(sessionService.getUserWithActualSession().getUserName());
		return loggerLogin;
	}
	
	private LoggerEvent createLoggerEvent() {
		LoggerEvent loggerEvent = new LoggerEvent();
		loggerEvent.setLogDate(Calendar.getInstance());
		loggerEvent.setEventDetails("Some detailed message.");
		return loggerEvent;
	}

	@Override
	protected void executeTests() throws Exception {
		givenLoggerLoginWhenStoreThenVerifyDatabaseResult();
		givenLoggerLoginWhenGetLoggerEventThenNull();
	}
	
	@SuppressWarnings("unchecked")
	private void givenLoggerLoginWhenStoreThenVerifyDatabaseResult() throws Exception {
		LoggerLogin loggerLogin = createLoggerLogin();
		jpaRepository.save(loggerLogin);
		LoggerEvent loggerEvent = createLoggerEvent();
		loggerEvent.setLoggerLogin(loggerLogin);
		jpaRepository.save(loggerEvent);
		Collection<LoggerLogin> collection = (Collection<LoggerLogin>) jpaRepository.loadAll(LoggerLogin.class);
		assertion("Collection should not be empty.", !collection.isEmpty());
		LoggerLogin insertedEntry = jpaRepository.find(loggerLogin, loggerLogin.getId());
		assertion("Inserted entry should not be null.", insertedEntry != null);
	}
	
	@SuppressWarnings("unchecked")
	private void givenLoggerLoginWhenGetLoggerEventThenNull() throws Exception {
		Collection<LoggerLogin> collection = (Collection<LoggerLogin>) jpaRepository.loadAll(LoggerLogin.class);
		assertion("Collection should not be empty.", !collection.isEmpty());
		Iterator<LoggerLogin> iterator = collection.iterator();
		while (iterator.hasNext()) {
			LoggerLogin loggerLogin = iterator.next();
			assertion("LoggerEvent should be null", loggerLogin.getLoggerEvents() == null);
		}
	}
}
