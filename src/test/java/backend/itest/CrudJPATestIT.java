package backend.itest;

import java.sql.Timestamp;

import backend.jpa.impl.JpaRepository;
import backend.jpa.newlog.entities.LoggerEvent;
import backend.jpa.newlog.entities.LoggerLogin;

public class CrudJPATestIT extends AbstractTestIT {
	
	private JpaRepository jpaRepository;
	private LoggerLogin loggerLogin;
	private LoggerEvent loggerEvent;
	
	public CrudJPATestIT(JpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
		this.loggerLogin = createLoggerLogin();
		this.loggerEvent = createLoggerEvent();
	}

	private LoggerLogin createLoggerLogin() {
		LoggerLogin loggerLogin = new LoggerLogin();
		loggerLogin.setAgentName("Mozzilla FireFox");
		loggerLogin.setLogDate(new Timestamp(System.currentTimeMillis()));
		return loggerLogin;
	}
	
	private LoggerEvent createLoggerEvent() {
		LoggerEvent loggerEvent = new LoggerEvent();
		loggerEvent.setLogDate(new Timestamp(System.currentTimeMillis()));
		loggerEvent.setEventDetails("Some detailed message.");
		return loggerEvent;
	}

	@Override
	protected void executeTests() throws Exception {
		givenLoggerLoginWhenStoreThenVerifyDatabaseResult();
	}
	
	private void givenLoggerLoginWhenStoreThenVerifyDatabaseResult() throws Exception {
		jpaRepository.save(loggerLogin);
		LoggerLogin databaseEntry = (LoggerLogin) jpaRepository.find(loggerEvent);
		assertion("Entry should not be null.", databaseEntry != null);
	}
}
