package backend.itest.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import backend.jpa.Entry;
import backend.jpa.impl.JpaRepository;
import backend.jpa.newlog.entity.LoggerEvent;
import backend.jpa.newlog.entity.LoggerLogin;
import backend.service.SessionService;

public class CrudJpaIT extends AbstractIT {
	
	private final JpaRepository jpaRepository;
	private final SessionService sessionService; 
	private List<Entry> cleanupList;
	
	public CrudJpaIT(JpaRepository jpaRepository, SessionService sessionService) {
		this.jpaRepository = jpaRepository;
		this.sessionService = sessionService;
		this.cleanupList = new LinkedList<>();
	}

	@Override
	protected void setup() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	protected void executeTests() throws Exception {
		givenLoggerLoginWhenStoreThenVerifyDatabaseResult();
	}
	
	@Override
	protected void cleanup() {
		cleanupList.stream().forEach(entry -> jpaRepository.delete(entry));
	}
	
	private LoggerLogin createLoggerLogin() {
		LoggerLogin loggerLogin = new LoggerLogin();
		loggerLogin.setAgentName("Mozilla FireFox");
		loggerLogin.setLogDate(LocalDateTime.now());
		loggerLogin.setUserName(sessionService.getUserWithActualSession().getUserName());
		return loggerLogin;
	}
	
	private LoggerEvent createLoggerEvent() {
		LoggerEvent loggerEvent = new LoggerEvent();
		loggerEvent.setLogDate(LocalDateTime.now());
		loggerEvent.setEventDetails("Some detailed message.");
		return loggerEvent;
	}

	@SuppressWarnings("unchecked")
	private void givenLoggerLoginWhenStoreThenVerifyDatabaseResult() throws Exception {
		displayTestName();
		
		cleanupList.clear();
		LoggerLogin loggerLogin = createLoggerLogin();
		jpaRepository.save(loggerLogin);
		LoggerEvent loggerEvent = createLoggerEvent();
		loggerEvent.setLoggerLogin(loggerLogin);
		jpaRepository.save(loggerEvent);
		Collection<LoggerLogin> collection = (Collection<LoggerLogin>) jpaRepository.loadAll(LoggerLogin.class);
		assertion("Collection should not be empty.", !collection.isEmpty());
		LoggerLogin insertedEntry = jpaRepository.find(loggerLogin, loggerLogin.getId());
		assertion("Inserted entry should not be null.", insertedEntry != null);
		cleanupList.add(loggerEvent );
		cleanupList.add(loggerLogin);
	}

}
