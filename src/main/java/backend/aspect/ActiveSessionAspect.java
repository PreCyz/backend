package backend.aspect;

import backend.annotation.SecurityGuard;
import backend.initiator.LoggerInitiator;
import backend.service.SessionService;

public class ActiveSessionAspect extends LoggerInitiator {
	
	private final SessionService sessionService;

	public ActiveSessionAspect(SessionService sessionService) {
		super();
		this.sessionService = sessionService;
	}

	public void checkActiveSession(SecurityGuard activeSession) {
		if( activeSession.activeSessionRequired() ) {
			sessionService.getUserWithActualSession();
		}
	}
}
