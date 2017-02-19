package backend.log;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.helper.MessageHelper;
import backend.jpa.entities.log.LogLogin;
import backend.jpa.entities.log.LogLogin.UserSessionEventType;

public class LogLoginTest {
	
	private LogLogin logLogin;
	private String userName = "someUserName_should_be_upper_case_after_set";
	private String userLogin = "someUserLogin_should_be_upper_case_after_set";
	private String sessionId = "someSessionId";
	private String userIp = "someUserIp";
	private String userAgent = "someUserAgent";
	private String key = "someKey";
	private String[] params = new String[]{};

	@Before
	public void setUp() throws Exception {
		logLogin = new LogLogin(userName, userLogin, sessionId, userIp, userAgent, key, params);
	}

	@After
	public void tearDown() throws Exception {
		logLogin = null;
	}

	@Test(expected = NullPointerException.class)
	public void givenNullKeyWhenNewLogLoginThenThrowNullPointerException() {
		key = null;
		logLogin = new LogLogin(userName, userLogin, sessionId, userIp, userAgent, key, params);
	}
	
	@Test
	public void givenAllParamsWhenNewLogLoginWithLoginSuccesThenCreateProperLogLogin() {
		UserSessionEventType type = UserSessionEventType.LOGIN_SUCCESS;
		logLogin = new LogLogin(userName, userLogin, sessionId, userIp, userAgent, type, params);
		assertNotNull(logLogin);
		assertEquals(MessageHelper.getMessage(UserSessionEventType.LOGIN_SUCCESS.getMessageKey(), null), logLogin.getMessageBase());
		assertTrue(logLogin.isStoreUserSessionId());
		assertEquals(userName.toUpperCase(), logLogin.getUserName());
		assertEquals(userLogin.toUpperCase(), logLogin.getUserLogin());
		
		type = UserSessionEventType.LOGIN_FAILURE;
		logLogin = new LogLogin(userName, userLogin, sessionId, userIp, userAgent, type, params);
		assertNotNull(logLogin);
		assertEquals(MessageHelper.getMessage(UserSessionEventType.LOGIN_SUCCESS.getMessageKey(), null), logLogin.getMessageBase());
		assertTrue(!logLogin.isStoreUserSessionId());
		assertEquals(userName.toUpperCase(), logLogin.getUserName());
		assertEquals(userLogin.toUpperCase(), logLogin.getUserLogin());
		
		type = null;
		logLogin = new LogLogin(userName, userLogin, sessionId, userIp, userAgent, type, params);
		assertNotNull(logLogin);
		assertEquals(MessageHelper.getMessage(UserSessionEventType.LOGIN_SUCCESS.getMessageKey(), null), logLogin.getMessageBase());
		assertTrue(!logLogin.isStoreUserSessionId());
		assertEquals(userName.toUpperCase(), logLogin.getUserName());
		assertEquals(userLogin.toUpperCase(), logLogin.getUserLogin());
	}
	
	@Test
	public void givenUserAgentWhenSetUserAgentThenSetProperAgentValue(){
		int stringSize = 0;
		userAgent = buildStringWithLength(stringSize);
		logLogin.setUserAgent(userAgent);
		assertEquals(userAgent, logLogin.getUserAgent());
		
		stringSize = LogLogin.USER_AGENT_MAX_LENGTH;
		userAgent = buildStringWithLength(stringSize);
		logLogin.setUserAgent(userAgent);
		assertEquals(userAgent, logLogin.getUserAgent());
		
		stringSize = LogLogin.USER_AGENT_MAX_LENGTH + getRandomInt();
		userAgent = buildStringWithLength(stringSize);
		logLogin.setUserAgent(userAgent);
		assertEquals(userAgent.substring(0, LogLogin.USER_AGENT_MAX_LENGTH), logLogin.getUserAgent());
	}
	
	private String buildStringWithLength(int stringLength){
		if(stringLength < 0){
			stringLength = 0;
		}
		StringBuilder builder = new StringBuilder();
		for(int i=0; i < stringLength; i++){
			builder.append("a");
		}
		return builder.toString();
	}
	
	private int getRandomInt(){
		return new Random().nextInt(LogLogin.USER_AGENT_MAX_LENGTH);
	}
}
