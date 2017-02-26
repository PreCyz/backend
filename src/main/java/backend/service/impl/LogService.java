package backend.service.impl;

import java.util.Collection;

import backend.jpa.Entry;
import backend.jpa.entity.log.LogEvent;
import backend.jpa.entity.log.LogLogin;
import backend.jpa.impl.JpaRepository;

public class LogService {
	
	private final JpaRepository jpaService;

	public LogService(JpaRepository jpaService) {
		this.jpaService = jpaService;
	}

	public Long saveLoggerLogin(LogLogin logLogin) {
		jpaService.save(logLogin);
		return logLogin.getId();
	}

	public void saveLoggerEvent(LogEvent logEvent) {
		jpaService.save(logEvent);
	}

	public void saveEventLogs(Collection<Entry> collection) {
		jpaService.saveCollection(collection);
	}

	public void saveLogs(Collection<Entry> collection) {
		jpaService.saveCollection(collection);
	}

}
