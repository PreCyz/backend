package backend.jpa.entities.log;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class LogLogin extends LogAbstract {

	private static final long serialVersionUID = -7440386269917560989L;
	public static final int USER_AGENT_MAX_LENGTH = 0;
	
	public enum UserSessionEventType {
		LOGIN_SUCCESS, LOGIN_FAILURE;

		public String getMessageKey() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public LogLogin() {}

	public LogLogin(String userName, String userLogin, String sessionId, String userIp, String userAgent, String key,
			String[] params) {
		// TODO Auto-generated constructor stub
	}
	
	public LogLogin(String userName, String userLogin, String sessionId, String userIp, String userAgent, 
			UserSessionEventType type, String[] params){
		
	}

	public LogLogin(String userName, String userLogin, String sessionId, String userIp, String userAgent,
			UserSessionEventType userSessionEventType) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void loadLazy() {
		// TODO Auto-generated method stub
		
	}

	public boolean isStoreUserSessionId() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserLogin() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setUserAgent(String userAgent) {
		// TODO Auto-generated method stub
		
	}

	public String getUserAgent() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLogDate(Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	public Long[] getToStoreId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLogEvents(List<LogEvent> list) {
		// TODO Auto-generated method stub
		
	}

	public void setMsgTemplate(Long msgTemplate) {
		// TODO Auto-generated method stub
		
	}

	public void setConcatenatedMessageParams(String params) {
		// TODO Auto-generated method stub
		
	}

	public List<LogEvent> getLogEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSessionId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserIp() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getMsgTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getConcatenatedMessageParams() {
		// TODO Auto-generated method stub
		return null;
	}

}
