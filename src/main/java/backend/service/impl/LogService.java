package backend.service.impl;

import java.util.Collection;

import backend.jpa.Entry;
import backend.jpa.impl.JpaRepository;
import backend.jpa.newlog.entities.LoggerEvent;
import backend.jpa.newlog.entities.LoggerLogin;

public class LogService {
	
	private final JpaRepository jpaService;

	public LogService(JpaRepository jpaService) {
		this.jpaService = jpaService;
	}

	public Long saveLoggerLogin(LoggerLogin loggerLogin) {
		jpaService.save(loggerLogin);
		return loggerLogin.getId();
	}

	public void saveLoggerEvent(LoggerEvent loggerEvent) {
		jpaService.save(loggerEvent);
	}

	public void saveEventLogs(Collection<Entry> collection) {
		jpaService.saveCollection(collection);
	}

	public void saveLogs(Collection<Entry> collection) {
		jpaService.saveCollection(collection);
	}

}
