package backend.log4j;

import java.sql.Timestamp;
import java.util.Vector;

import javax.servlet.ServletException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import backend.dto.LoggedUser;
import backend.helper.StringHelper;
import backend.jpa.entities.log.LogDBEvent;
import backend.jpa.entities.log.LogEvent;
import backend.jpa.entities.log.LogLogin;
import backend.jpa.entities.log.LogSequenceEntry;
import backend.jpa.entities.log.LoggingEventTask;
import backend.service.SessionService;
import backend.service.impl.LogService;
import backend.util.LoggerInfo;
import backend.util.LoggerInfo.LoggerInfoData;

public class TestLoggingAppender extends AppenderSkeleton implements Appender {

public static final String LOG_LOGIN_ID_SESSION_KEY = "LOG_LOGIN_ID_SESSION_KEY";
	
	@Autowired
	private LogService logService;
	@Autowired
	private SessionService sessionService;
	
	private ApplicationContext context;
	
	/**
	 * WARNING: might return null if Spring context is not yet initialised 
	 * @return
	 */
	public void refreshSpringBeans() {
		context = new ClassPathXmlApplicationContext("classpath:/META-INF/test-spring-config.xml");
		logService = context.getBean(LogService.class);
		sessionService = context.getBean(SessionService.class);
	}
	
	protected void append(LoggingEvent loggingEvent) {
		LoggerInfoData loggerData = LoggerInfo.getActualLoggerData();

		if (loggingEvent.getMessage() != null && loggingEvent.getMessage() instanceof LogLogin) {
			LogLogin logLogin = (LogLogin)loggingEvent.getMessage();
			
			prepareLogLogin(loggingEvent, logLogin);
		
			if (logService == null) {
				refreshSpringBeans();
			}
			Long savedLogLoginId = logService.saveLogLogin(logLogin);
			
			if (logLogin.isStoreUserSessionId()) {
				//TODO FIXME choose only one method
				if (logLogin.getToStoreId() != null) {
					logLogin.getToStoreId()[0] = savedLogLoginId;
				}
				if (sessionService == null) {
					refreshSpringBeans();
				}
				LoggedUser user = sessionService.getUserWithActualSession();
				if (user != null) {
					user.setSessionReferenceId(savedLogLoginId);
				}
			}
				
			return; 
		}
		
		//sprawdzamy czy zdarzenie nie jest zdarzeniem logowania taska do ULLA, jeśli tak, to logujemy po staremu
		if (loggerData != null && loggerData.getTaskId() != null && Level.DEBUG.equals(loggingEvent.getLevel())) {

			LoggingEventTask taskEvent = new LoggingEventTask();
			taskEvent.setMessage(loggingEvent.getRenderedMessage());
			taskEvent.setUllaTaskId(loggerData.getTaskId());
			
			bufferAndSaveLoggingTask(taskEvent);
			return;
		} 
		
		LogEvent logEvent = null;
		
		if (loggingEvent.getMessage() instanceof LogEvent) {
			logEvent = (LogEvent)loggingEvent.getMessage();
		} else {
			if (loggingEvent.getMessage() instanceof Exception) {
				Exception e = (Exception) loggingEvent.getMessage();
				
				logEvent = new LogEvent("sol.log.unexpected.exception", e.getMessage(), e.getClass().getName());
			} else if (Level.ERROR.equals(loggingEvent.getLevel())) {
				if (loggingEvent.getThrowableInformation() != null && loggingEvent.getThrowableInformation().getThrowable() != null) {
					logEvent = new LogEvent("sol.log.exception", loggingEvent.getMessage().toString(), 
							loggingEvent.getThrowableInformation().getThrowable().getMessage(),
							loggingEvent.getThrowableInformation().getThrowable().getClass().getName());
				} else {
					logEvent = new LogEvent("sol.log.error", loggingEvent.getMessage().toString());
				}
				
			}
		}
		if(logEvent != null) {
			prepareAndSaveLogEvent(logEvent, loggingEvent);
		}
	}
	
	private void prepareLogLogin(LoggingEvent loggingEvent, LogLogin logLogin) {
		logLogin.setLogDate(new Timestamp(loggingEvent.getTimeStamp()));
		
		ThrowableInformation throwableInformation = loggingEvent.getThrowableInformation();
		if (throwableInformation != null) {
			simpleAddThrowableCauseToLogLogin(logLogin, throwableInformation.getThrowable());
		}
	}
	public static void simpleAddThrowableCauseToLogLogin(LogLogin logLogin, Throwable throwable) {
		if (throwable != null) {
			try {
				if (throwable.getCause() != null) {
					logLogin.addExtraParameter(throwable.getCause().getMessage());
				} else {
					logLogin.addExtraParameter(throwable.getMessage());
				}
			} catch (Throwable e) {}//ignore
		}
	}
	
	
	
	private void prepareAndSaveLogEvent(LogEvent logEvent, LoggingEvent loggingEvent) {
		LogDBEvent log = new LogDBEvent();
		logEvent.copyParams(log);
		
		copyDataFromLog4j(loggingEvent, log);
		
		copyDataFromApplicationContexts(log);
		
		bufferAndSave(log);
	}

	private void copyDataFromLog4j(LoggingEvent loggingEvent, LogDBEvent log) {
		//log.setLogDate(new Date(loggingEvent.timeStamp));
		log.setLogDate(new Timestamp(loggingEvent.getTimeStamp()));
		
		log.setLevel(loggingEvent.getLevel());
		
		//for transaction messages (level WARN) we want to be double sure, at least for some time.
		if (Level.WARN.equals(loggingEvent.getLevel()) && log.getExplicitMessage() == null) {
			log.addExplicitMessage(log.toString());
		}
		
		log.setLineNumber(loggingEvent.getLocationInformation().getLineNumber());
		log.setFileNameAndMethodName(loggingEvent.getLocationInformation().getFileName(), 
									loggingEvent.getLocationInformation().getMethodName());
		
		if (!log.isIgnoreStack()) { 
			prepareStackData(loggingEvent, log);
		}
	}

	private void prepareStackData(LoggingEvent loggingEvent, LogDBEvent log) {
		String[] throwableStrRep = loggingEvent.getThrowableStrRep();
		if (throwableStrRep != null) {
			Throwable throwable = null;
			if (loggingEvent.getThrowableInformation() != null) {
				throwable = loggingEvent.getThrowableInformation().getThrowable();
			}
			if (throwable != null && throwable instanceof ServletException) {
				ThrowableInformation util = new ThrowableInformation(((ServletException)throwable).getRootCause());
				String[] rootStrRep = util.getThrowableStrRep();
				if (rootStrRep != null && rootStrRep.length > 0) {
					throwableStrRep = (String[]) ArrayUtils.addAll(throwableStrRep, rootStrRep);
				}
			}
			int j = 0;
			for (String throwableMessage : throwableStrRep) {
				log.addThrowableMessage(j++, throwableMessage);
			}
		}
	}
	
	private void copyDataFromApplicationContexts(LogDBEvent log) {
		LoggerInfoData loggerData = LoggerInfo.getActualLoggerData();
		if (loggerData != null) {
			if (loggerData.getFunction() != null) {
				log.setDaoFunction(loggerData.getClassName(), loggerData.getFunction());
			}
			setUllaTaskId(log, loggerData);
		}
		
		setContractNumber(log);
		
		setSessionId(log);
	}
	
	private static void setUllaTaskId(LogEvent log, LoggerInfoData loggerData) {
		String ullaTaskId = loggerData.getTaskId();
		String ullaTaskIDSetByProgrammer = log.getUllaTaskId();
		if (ullaTaskIDSetByProgrammer == null) {
			log.setUllaTaskId(ullaTaskId);
		} else if (!ullaTaskIDSetByProgrammer.equals(ullaTaskId)) {
			log.addExtraParameter(String.format("ULLA task ID: %s", ullaTaskId));
		}
	}
	
	private void setContractNumber(LogEvent log) {
		if(sessionService != null) {
			String contractNumberSetByProgrammer = log.getContractNumber();
			String contractNumber = sessionService.getFromSession("contractNumber", String.class);
			if (contractNumberSetByProgrammer == null) {
				log.setContractNumber(contractNumber);
			} else if (! contractNumberSetByProgrammer.equals(contractNumber)) {
				log.addExtraParameter(String.format("contract number: %s", contractNumber));
			}
		}
	}
	
	private void setSessionId(LogDBEvent log) {
		if(sessionService != null){
			String sessionId = sessionService.getSessionId();
			if (StringHelper.notEmpty(sessionId) && sessionService.getFromSession(LOG_LOGIN_ID_SESSION_KEY, Long[].class) != null) {
				log.setSessionRefernceId(sessionService.getFromSession(LOG_LOGIN_ID_SESSION_KEY, Long[].class)[0]);
			}
		}
	}
	
	private static Vector<LogDBEvent> buffer = new Vector<LogDBEvent>();
	private static Boolean appending = Boolean.FALSE;
	
	@SuppressWarnings("unchecked")
	private void bufferAndSave(LogDBEvent log) {
		/*
		 * Ensure exclusive access to the buffer in case another thread is
		 * currently writing the buffer.
		 */
		synchronized (buffer) {
			// Add the current event into the buffer
			buffer.add(log);
		}

		/*
		 * Ensure exclusive access to the appending flag to guarantee that it
		 * doesn't change in between checking it's value and setting it
		 */
		synchronized (appending) {
			if (logService == null) {
				refreshSpringBeans();
			}

			if (!appending.booleanValue() && logService != null) {
				/*
				 * No other thread is appending to the log, so this thread can
				 * perform the append
				 */
				appending = Boolean.TRUE;
			} else {
				/*
				 * Another thread is already appending to the log and it will
				 * take care of emptying the buffer
				 */
				return;
			}
		}

		/*
		 * Ensure exclusive access to the buffer in case another thread is
		 * currently adding to the buffer.
		 */
		Vector<LogSequenceEntry> copy;
		synchronized (buffer) {
			copy = (Vector<LogSequenceEntry>) buffer.clone();
			buffer.clear();
		}

		logService.saveEventLogs(copy);

		/*
		 * Ensure exclusive access to the appending flag - this really
		 * shouldn't be needed as the only other check on this flag is also
		 * synchronized on the buffer. We don't want to do this in the
		 * finally block as between here and the finally block we will not
		 * be synchronized on the buffer and another process could add an
		 * event to the buffer, but the appending flag will still be true,
		 * so that event would not get written until another log event
		 * triggers the buffer to be emptied.
		 */
		synchronized (appending) {
			appending = Boolean.FALSE;
		}
	}
	
	
	//from old appender

	private static Vector<LoggingEventTask> oldBuffer = new Vector<LoggingEventTask>();
	private static Boolean oldAppending = Boolean.FALSE;

	@SuppressWarnings("unchecked")
	protected void bufferAndSaveLoggingTask(LoggingEventTask loggingEventTask) {

		/*
		 * Ensure exclusive access to the buffer in case another thread is
		 * currently writing the buffer.
		 */
		synchronized (oldBuffer) {
			// Add the current event into the buffer
			oldBuffer.add(loggingEventTask);
		}

		/*
		 * Ensure exclusive access to the appending flag to guarantee that it
		 * doesn't change in between checking it's value and setting it
		 */
		synchronized (oldAppending) {
			if (logService == null) {
				refreshSpringBeans();
			}

			if (!oldAppending.booleanValue() && logService != null) {
				/*
				 * No other thread is appending to the log, so this thread can
				 * perform the append
				 */
				oldAppending = Boolean.TRUE;
			} else {
				/*
				 * Another thread is already appending to the log and it will
				 * take care of emptying the buffer
				 */
				return;
			}
		}

		try {
			/*
			 * Ensure exclusive access to the buffer in case another thread is
			 * currently adding to the buffer.
			 */
			Vector<LogSequenceEntry> copy;
			synchronized (oldBuffer) {
				copy = (Vector<LogSequenceEntry>) oldBuffer.clone();
				oldBuffer.clear();
			}

			/*
			 * Get the current buffer length. We only want to process events
			 * that are currently in the buffer. If events get added to the
			 * buffer after this point, they must have been caused by this loop,
			 * as we have synchronized on the buffer, so no other thread could
			 * be adding an event. Any events that get added to the buffer as a
			 * result of this loop will be discarded, as to attempt to process
			 * them will result in an infinite loop.
			 */

			logService.saveLogs(copy);

			/*
			 * Ensure exclusive access to the appending flag - this really
			 * shouldn't be needed as the only other check on this flag is also
			 * synchronized on the buffer. We don't want to do this in the
			 * finally block as between here and the finally block we will not
			 * be synchronized on the buffer and another process could add an
			 * event to the buffer, but the appending flag will still be true,
			 * so that event would not get written until another log event
			 * triggers the buffer to be emptied.
			 */
			synchronized (oldAppending) {
				oldAppending = Boolean.FALSE;
			}
		} catch (Exception he) {
			this.errorHandler.error("HibernateException", he, ErrorCode.GENERIC_FAILURE);
			// Reset the appending flag
			oldAppending = Boolean.FALSE;
			return;
		}
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#close()
	 */
	public void close() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#requiresLayout()
	 */
	public boolean requiresLayout() {
		return false;
	}
}
