package backend.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggerInfo {
	private static final ThreadLocal<LoggerInfoData> threadLocal = new ThreadLocal<LoggerInfoData>();
	
	private static final Log logger = LogFactory.getLog(LoggerInfo.class);
	
	private String className;
	
	private LoggerInfo(String className) {
		this.className = className;
	}

	public static LoggerInfo getInstance(Class<? extends Object> classType) {
		return new LoggerInfo(classType.getSimpleName());
	}
	
	public static LoggerInfoData initData() {
		LoggerInfoData data = threadLocal.get();
		if (data == null) {
			data = new LoggerInfoData();
		}
		threadLocal.set(data);
		return data;
	}
		
	public static void setActualULLATaskId(String taskId) {
		LoggerInfoData data = initData();
		data.taskId = taskId;
	}
	
	public static void setActualSessionId(String sessionId) {
		LoggerInfoData data = initData();
		data.sessionId = sessionId;
	}

	public static void setActualUserLogin(String userLogin) {
		LoggerInfoData data = initData();
		data.userLogin = userLogin;
	}
	
	public static void setActualUserIp(String userIp) {
		LoggerInfoData data = initData();
		data.userIp = userIp;
	}
	
	public static LoggerInfoData getActualLoggerData() {
		return threadLocal.get();
	}
	
	public void cleanActualData() {
		threadLocal.set(null);
	}
	
	public static void staticCleanActualData() {
		threadLocal.set(null);
	}
	
	public static class LoggerInfoData {
		private String className;
		private String function;
		private String taskId;
		/**/
		private String sessionId;
		/**/
		private String userLogin;
		private String userIp;
		public String getClassName() {
			return className;
		}
		public String getFunction() {
			return function;
		}
		public String getTaskId() {
			return taskId;
		}
		public String getSessionId() {
			return sessionId;
		}
		public String getUserLogin() {
			return userLogin;
		}
		public String getUserIp() {
			return userIp;
		}
	}
}
