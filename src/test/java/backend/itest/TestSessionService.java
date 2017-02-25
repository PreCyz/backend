package backend.itest;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import backend.dto.LoggedUser;
import backend.exception.DAOException;
import backend.service.SessionService;

public final class TestSessionService implements SessionService {
	
	private static Map<String, Object> sessionMap = new HashMap<>();
	
	public void addToSession(String key, Object value) {
		sessionMap.put(key, value);
	}
	
	public HttpSession getSession() {
		System.out.println("getSession() - this method should not be used during integration tests. Check where exactly method is used and fix it !!!");
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <A> A getFromSession(String key, Class<A> type) {
		return (A) sessionMap.get(key);
	}
	
	public String getSessionId(){
		return "SomeSessionID";
	}

	public LoggedUser getUserWithActualSession() {
		String sessionId = getSessionId();
		LoggedUser user = getFromSession(sessionId, LoggedUser.class);
		if(hasUserWrongSession(sessionId, user)){
			throw new DAOException("User does not have actual session.", EXCEPTION_MESSAGE_PREFIX + "user").setNotLog();
		}
		return user;
	}
	
	@PostConstruct
	public void initService() {
		LoggedUser user = new LoggedUser();
		user.setUserName("TEST_userName");
		user.setPassword("TestPassword");
		user.setUserLogin("testLogin");
		user.setSessionId(getSessionId());
		sessionMap.put(getSessionId(), user);
	}

	@Override
	public boolean hasUserWrongSession(String sessionId, LoggedUser user) {
		return user == null || !sessionId.equals(user.getSessionId());
	}

	@Override
	public void removeFromSession(String key) {
		sessionMap.remove(key);
	}

	@Override
	public void destroySession() {
		sessionMap.clear();
	}

}
