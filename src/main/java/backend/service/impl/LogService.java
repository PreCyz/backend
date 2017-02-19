package backend.service.impl;

import java.util.Collection;

import backend.jpa.entities.log.LogDBEvent;
import backend.jpa.entities.log.LogLogin;
import backend.jpa.entities.log.LogSequenceEntry;
import backend.jpa.impl.JpaRepository;

public class LogService {
	
	private final JpaRepository jpaService;

	public LogService(JpaRepository jpaService) {
		this.jpaService = jpaService;
	}

	public Long saveLogLogin(LogLogin logLogin) {
		jpaService.save(logLogin);
		return logLogin.getId();
	}

	public void saveLogEvent(LogDBEvent logEvent) {
		jpaService.save(logEvent);
	}

	public void saveEventLogs(Collection<LogSequenceEntry> collection) {
		jpaService.saveCollection(collection);
	}

	public void saveLogs(Collection<LogSequenceEntry> collection) {
		jpaService.saveCollection(collection);
	}

}
