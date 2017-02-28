package backend.itest.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import backend.jpa.Entry;
import backend.jpa.entity.log.LogEvent;
import backend.jpa.entity.log.LogLogin;
import backend.jpa.impl.JpaRepository;
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
	
	private LogLogin createLoggerLogin() {
		LogLogin logLogin = new LogLogin();
		logLogin.setAgentName("Mozilla FireFox");
		logLogin.setLogDate(LocalDateTime.now());
		logLogin.setUserName(sessionService.getUserWithActualSession().getUserLogin());
		return logLogin;
	}
	
	private LogEvent createLoggerEvent() {
		LogEvent logEvent = new LogEvent();
		logEvent.setLogDate(LocalDateTime.now());
		logEvent.setEventDetails("Some detailed message.");
		return logEvent;
	}

	@SuppressWarnings("unchecked")
	private void givenLoggerLoginWhenStoreThenVerifyDatabaseResult() throws Exception {
		displayTestName();
		
		cleanupList.clear();
		LogLogin logLogin = createLoggerLogin();
		jpaRepository.save(logLogin);
		LogEvent logEvent = createLoggerEvent();
		logEvent.setLoggerLogin(logLogin);
		jpaRepository.save(logEvent);
		Collection<LogLogin> collection = (Collection<LogLogin>) jpaRepository.loadAll(LogLogin.class);
		assertion("Collection should not be empty.", !collection.isEmpty());
		LogLogin insertedEntry = jpaRepository.find(logLogin, logLogin.getId());
		assertion("Inserted entry should not be null.", insertedEntry != null);
		cleanupList.add(logEvent );
		cleanupList.add(logLogin);
	}

}
