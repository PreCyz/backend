package backend.integration.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import backend.helper.CollectionHelper;
import backend.jpa.entities.SolContractInfo;
import backend.jpa.entities.log.LogAbstract;
import backend.jpa.entities.log.LogDBEvent;
import backend.jpa.entities.log.LogEvent;
import backend.jpa.entities.log.LogLogin;
import backend.jpa.entities.log.LogSQLEvent;
import backend.jpa.entities.log.LogSequenceEntry;
import backend.jpa.entities.log.LogLogin.UserSessionEventType;
import backend.jpa.impl.JpaRepository;

public class CrudJPAIntegrationTest extends AbstractIntegrationTest {
	
	private JpaRepository jpaService;
	private LogLogin logLogin;
	private LogDBEvent logEvent;
	
	private Long recordId; 
	
	private final String userName = "testUserName";
	private final String userLogin = "testUserLogin";
	private final String sessionId = "testSessionId";
	private final String userIp = "testUserIp";
	private final String userAgent = "testUserAgent";
	private final String params = "~myParam1~secondParam2~";
	private final String level = "W";
	private final String contractNumber = "contractNumber";
	private final String ullaTaskId = "ullaTaskId";
	private final String message = "Some message";
	private final UserSessionEventType userSessionEventType = UserSessionEventType.LOGIN_SUCCESS;
	private final Long msgTemplate = 1L;
	private final Long fileMethod = 4L;
	private final Long lineNumber = 1L;
	
	public CrudJPAIntegrationTest(JpaRepository jpaService) {
		this.jpaService = jpaService;
		this.logLogin = createLogLogin();
		this.logEvent = createNewLogDBEvent();
	}

	private LogLogin createLogLogin() {
		LogLogin newLogLogin = new LogLogin(userName, userLogin, sessionId, userIp, userAgent, userSessionEventType);
		newLogLogin.setMsgTemplate(msgTemplate);
		newLogLogin.setConcatenatedMessageParams(params);
		return newLogLogin;
	}
	
	private LogDBEvent createNewLogDBEvent() {
		LogDBEvent newLogDBEvent = new LogDBEvent();
		newLogDBEvent.setMsgTemplate(msgTemplate);
		newLogDBEvent.setFileMethod(fileMethod);
		newLogDBEvent.setLevel(level);
		newLogDBEvent.setConcatenatedMessageParams(params);
		newLogDBEvent.setMessage(message);
		newLogDBEvent.setLineNumber(lineNumber);
		newLogDBEvent.setContractNumber(contractNumber);
		newLogDBEvent.setUllaTaskId(ullaTaskId);
		return newLogDBEvent;
	}

	@Override
	protected void executeTests() throws Exception {
		givenJPACRUDAndLogLoginWhenSaveThenCreateNewRecordInDatabaseAndReturnItsId();
		givenJPACRUDAndRecordIdWhenLoadLogLoginThenReturnRecord();
		givenJpaCrudAndLogAbstractWhenSaveThenUpdateExistingRecordInDatabaseAndReturnItsId(logLogin, LogLogin.class);
		givenJpaCrudAndLogLoginWhenDeleteThenDeleteRecordFromDatabase();
		givenJpaCrudAndLogEntryWhenLoadAllThenReturnCollectionOfLogEntry(LogLogin.class);
		
		System.out.println();
		recordId = null;
		givenLogDBEventWhenSaveThenCreateNewRecordAndReturnItsId();
		givenJPACRUDAndRecordIdWhenLoadLogEventThenReturnRecord();
		givenJpaCrudAndLogAbstractWhenSaveThenUpdateExistingRecordInDatabaseAndReturnItsId(logEvent, LogDBEvent.class);
		givenJpaCrudAndLogEntryWhenLoadAllThenReturnCollectionOfLogEntry(LogDBEvent.class);
		
		recordId = null;
		givenLogEventsWhenSaveThenCreateNewRecordsInDataBase();
		givenExistingLogLoginIdWhenLoadThenReturnLogLoginWithLogEvents();
		
		givenSolContractInfoWhenSaveThenInsertToDataBase();
	}
	
	private void givenJPACRUDAndLogLoginWhenSaveThenCreateNewRecordInDatabaseAndReturnItsId() throws Exception {
		displayTestName();
		
		jpaService.save(logLogin);
		recordId = logLogin.getId();
		
		String msg = String.format("Utworzono w bazie danych nowy rekord %s o id %d.", logLogin.getClass().getSimpleName(), recordId);
		
		boolean condition = recordId > 0;
		
		assertion(msg, condition);
	}
	
	private void givenJPACRUDAndRecordIdWhenLoadLogLoginThenReturnRecord() throws Exception {
		displayTestName();
		
		LogLogin someRecord = new LogLogin();
		someRecord.setId(recordId);
		LogLogin record = (LogLogin) jpaService.find(someRecord);
		
		boolean condition =  userName.equalsIgnoreCase(record.getUserName());
		condition &= userLogin.equalsIgnoreCase(record.getUserLogin());
		condition &= sessionId.equalsIgnoreCase(record.getSessionId());
		condition &= userIp.equalsIgnoreCase(record.getUserIp());
		condition &= userAgent.equalsIgnoreCase(record.getUserAgent());
		condition &= msgTemplate.equals(record.getMsgTemplate());
		condition &= record.getLogDate() != null;
		condition &= params.equals(record.getConcatenatedMessageParams());
		
		String msg = String.format("Pobrano z bazy danych rekord %s o id %d.", record.getClass().getSimpleName(), recordId);
		assertion(msg, condition);
	}
	
	@SuppressWarnings("unchecked")
	private <T> void givenJpaCrudAndLogAbstractWhenSaveThenUpdateExistingRecordInDatabaseAndReturnItsId(LogAbstract logEntry, T type) throws Exception {
		displayTestName();
		
		Long msgTemplateToUpdate = 6L;
		logEntry.setMsgTemplate(msgTemplateToUpdate);
		logEntry.setId(recordId);
		jpaService.save(logEntry);
		Long actualId = logEntry.getId();
		T resultRecord = (T) jpaService.find(logEntry);
		
		boolean condition = recordId == actualId;
		condition &= msgTemplateToUpdate.equals(((LogAbstract)resultRecord).getMsgTemplate());
		
		String msg = String.format("Zaktualizowano w bazie danych rekord %s o id %d.", resultRecord.getClass().getSimpleName(), actualId);
		assertion(msg, condition);
	}
	
	private void givenJpaCrudAndLogLoginWhenDeleteThenDeleteRecordFromDatabase() throws Exception {
		displayTestName();
		
		logLogin.setId(recordId);
		jpaService.delete(logLogin);
		LogLogin resultRecord = (LogLogin) jpaService.find(logLogin);
		boolean condition = resultRecord == null;
		String msg = String.format("Skasowano z bazy danych rekord %s o id %d.", logLogin.getClass().getSimpleName(), recordId);
		assertion(msg, condition);
	}
	
	@SuppressWarnings("unchecked")
	private <T> void givenJpaCrudAndLogEntryWhenLoadAllThenReturnCollectionOfLogEntry(T type) throws Exception {
		displayTestName();
		
		List<T> list = new ArrayList<>();
		Collection<T> resultCollection = (Collection<T>) jpaService.loadAll((Class<? extends LogSequenceEntry>) type);
		resultCollection.forEach(obj -> list.add( obj ));
		
		boolean condition = list != null;
		if (condition) {
			condition &= !list.isEmpty();
			//condition &= list.get(0) instanceof LogEntry;
		}
		
		String msg = String.format("Z bazy danych pobrano %d rekordów %s.", list.size(), list.get(0).getClass().getSimpleName());
		assertion(msg, condition);
	}
	
	private void givenLogDBEventWhenSaveThenCreateNewRecordAndReturnItsId(){
		displayTestName();
		jpaService.save(logEvent);
		recordId = logEvent.getId();
		String msg = String.format("Utworzono w bazie danych nowy rekord %s o id %d.", logEvent.getClass().getSimpleName(), recordId);
		
		boolean condition = recordId > 0;
		
		assertion(msg, condition);
	}
	
	private void givenJPACRUDAndRecordIdWhenLoadLogEventThenReturnRecord() throws Exception {
		displayTestName();
		LogDBEvent someRecord = new LogDBEvent();
		someRecord.setId(recordId);
		
		LogDBEvent record = (LogDBEvent) jpaService.find(someRecord);
		
		boolean condition = msgTemplate == record.getMsgTemplate();
		condition &= fileMethod == record.getFileMethod();
		condition &= null == record.getDaoFunction();
		condition &= level.equals(record.getLevel());
		condition &= params.equalsIgnoreCase(record.getConcatenatedMessageParams());
		condition &= message.equalsIgnoreCase(record.getMessage());
		condition &= lineNumber == record.getLineNumber();
		condition &= record.getLogDate() != null;
		condition &= contractNumber.equalsIgnoreCase(record.getContractNumber());
		condition &= ullaTaskId.equals(record.getUllaTaskId());
		
		String msg = String.format("Pobrano z bazy danych rekord %s o id %d.", record.getClass().getSimpleName(), recordId);
		assertion(msg, condition);
	}
	
	private void givenLogEventsWhenSaveThenCreateNewRecordsInDataBase() throws Exception {
		displayTestName();
		
		LogLogin newLogLogin = createLogLogin();
		List<LogEvent> events = new ArrayList<>(2);
		
		LogEvent sqlEvent = new LogSQLEvent("select * from dual where id = ?");
		sqlEvent.setContractNumber("sqlEvent");
		sqlEvent.setLevel("W");
		sqlEvent.setLogLogin(newLogLogin);
		
		LogEvent sqlEvent2 = new LogSQLEvent("select * from dual2 where id = ?");
		sqlEvent2.setContractNumber("sqlEvent2");
		sqlEvent2.setLevel("W");
		sqlEvent2.setLogLogin(newLogLogin);
		
		events.add(sqlEvent);
		events.add(sqlEvent2);
		newLogLogin.setLogEvents(events);
		
		jpaService.save(newLogLogin);
		Long eventId3 = newLogLogin.getId();
		jpaService.save(sqlEvent);
		Long eventId1 = sqlEvent.getId();
		jpaService.save(sqlEvent2);
		Long eventId2 = sqlEvent2.getId();
		
		boolean condition = eventId1 > 0L;
		condition &= eventId2 > 0L;
		condition &= eventId3 > 0L;
		
		String msg = String.format("Utworzono w bazie dwa recordy %s o id [%d,%d].", LogEvent.class.getSimpleName(), eventId1, eventId2);
		assertion(msg, condition);
	}
	
	private void givenExistingLogLoginIdWhenLoadThenReturnLogLoginWithLogEvents() throws Exception {
		displayTestName();
		
		final Long existingRecordId = 3856781L;
		LogLogin llFromDb = new LogLogin();
		llFromDb.setId(existingRecordId);
		llFromDb = (LogLogin) jpaService.find(llFromDb); 
		
		boolean condition = llFromDb != null;
		if (condition) {
			condition &= llFromDb.getId().equals(existingRecordId);
			condition &= !CollectionHelper.empty(llFromDb.getLogEvents());
			condition &= llFromDb.getLogEvents().get(0) != null;
		}
		
		int size = llFromDb == null ? 0 : llFromDb.getLogEvents().size();
		String msg = String.format("Z bazy danych pobrano obiekt %s który zawiera %d obiektów %s.", LogLogin.class.getSimpleName(), size, LogEvent.class.getSimpleName());
		assertion(msg, condition);
	}
	
	private void givenSolContractInfoWhenSaveThenInsertToDataBase() throws Exception {
		displayTestName();
		
		final String contractNo = "TESTCTRACT";
		final Boolean boolValue = Boolean.TRUE;
		
		SolContractInfo sci = new SolContractInfo();
		sci.setContractNumber(contractNo);
		sci.setFullSurrenderOrdered(boolValue);
		jpaService.insert(sci);
		
		SolContractInfo result = jpaService.find(sci, contractNo);
		boolean condition = result != null;
		if (condition) {
			condition &= contractNo.equals(result.getContractNumber());
			condition &= boolValue == result.isFullSurrenderOrdered();
		}
		jpaService.delete(sci, sci.getContractNumber());
		
		assertion("Obiekt SolContractInfo zapisany do i pobrany z bazy danych.", condition);
	}
}
