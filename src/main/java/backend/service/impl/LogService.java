package backend.service.impl;

import java.util.Collection;

import backend.jpa.Entry;
import backend.jpa.entity.log.LogLogin;
import backend.jpa.impl.JpaRepository;
import backend.service.SessionService;

public class LogService {
	
	private final JpaRepository jpaRepository;
	private SessionService sessionService;

	public LogService(JpaRepository jpaService) {
		this.jpaRepository = jpaService;
	}
	
	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	public Collection<Entry> saveLogs(Collection<Entry> collection) {
		jpaRepository.saveCollection(collection);
		return collection;
	}

	public Entry saveLog(Entry entry) {
		jpaRepository.save(entry);
		addLogLoginIdToSession(entry);
		return entry;
	}
	
	private void addLogLoginIdToSession(Entry entry) {
		if (entry instanceof LogLogin) {
			sessionService.addLogLoginId(entry.getId());
		}
	}

}
