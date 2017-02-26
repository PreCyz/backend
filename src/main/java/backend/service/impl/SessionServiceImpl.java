package backend.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

import backend.dto.LoggedUser;
import backend.exception.ApplicationUncheckedException;
import backend.helper.MessageHelper;
import backend.helper.StringHelper;
import backend.service.SessionService;

public class SessionServiceImpl implements SessionService {
	
	private static final Log logger = LogFactory.getLog(SessionServiceImpl.class); 
	
	private HttpSession session;
	
	public <T> void addToSession(String key, T value) {
		HttpSession session = getSession();
		throwExceptionIfNoSession(session);
		session.setAttribute(key, value);
	}

	private void throwExceptionIfNoSession(HttpSession session) {
		if (session == null) {
			String errMsg = "No session";
			logger.error(errMsg);
			throw new WebServiceException(errMsg);
		}
	}
	
	public HttpSession getSession() {
		HttpServletRequest req = getRequest();
		if (req == null) {
			return session;
		}
		return session = req.getSession();
	}
	
	private HttpServletRequest getRequest() {
		Message message = PhaseInterceptorChain.getCurrentMessage();
		HttpServletRequest req = (HttpServletRequest)message.get(AbstractHTTPDestination.HTTP_REQUEST);
		return req;
	}
	
	@SuppressWarnings("unchecked")
	public <A> A getFromSession(String key, Class<A> type) {
		HttpSession session = getSession();
		throwExceptionIfNoSession(session);
		return (A)session.getAttribute(key);
	}
	
	public String getSessionId() {
		return getSession().getId();
	}

	public LoggedUser getUserWithActualSession() {
		String sessionId = getSessionId();
		if(StringHelper.empty(sessionId)) {
			throw new ApplicationUncheckedException(MessageHelper.getMessage(getMsgKey("session"), null), getMsgKey("session"));
		}
		LoggedUser user = getFromSession(sessionId, LoggedUser.class);
		if(hasUserWrongSession(sessionId, user)) {
			throw new ApplicationUncheckedException(MessageHelper.getMessage(getMsgKey("user"), null), getMsgKey("user"));
		}
		return user;
	}
	
	private String getMsgKey(String suffix) {
		return EXCEPTION_MESSAGE_PREFIX + suffix;
	}
	
	public boolean hasUserWrongSession(String sessionId, LoggedUser user) {
		return user == null || !sessionId.equals(user.getSessionId());
	}

	@Override
	public void removeFromSession(String key) {
		HttpSession session = getSession();
		throwExceptionIfNoSession(session);
		session.removeAttribute(key);
	}

	@Override
	public void destroySession() {
		removeFromSession(getSessionId());
		session = null;
	}
}
