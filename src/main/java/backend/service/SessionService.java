package backend.service;

import javax.servlet.http.HttpSession;

import backend.dto.LoggedUser;

public interface SessionService {
	
	static final String EXCEPTION_MESSAGE_PREFIX = "backend.session.service.invalid.";
	static final String CONTRACT_NUMBER = "contractNumber";
	static final String LOG_LOGIN_ID = "logLoginId";
	
	HttpSession getSession();
	String getSessionId();

	<T> void addToSession(String key, T value);
	<A> A getFromSession(String key, Class<A> type);
	void removeFromSession(String key);
	
	LoggedUser getUserWithActualSession();
	
	boolean hasUserWrongSession(String sessionId, LoggedUser user);
	
	void destroySession();
	
	default void addUserContractToSession(String contractNumber) {
		addToSession(prepareKeyForContract(), contractNumber);
	}
	
	default String prepareKeyForContract() {
		LoggedUser user = getUserWithActualSession();
		return String.format("%s_%s", user.getUserLogin(), CONTRACT_NUMBER);
	}
	
	default void removeUserContractFromSession() {
		removeFromSession(prepareKeyForContract());
	}
	
	default void addLogLoginId(Long id) {
		addToSession(LOG_LOGIN_ID, id);
	}
}