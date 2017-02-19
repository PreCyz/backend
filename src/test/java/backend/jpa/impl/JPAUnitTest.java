package backend.jpa.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;

import backend.AbstractUnitTest;
import backend.jpa.entities.log.LogDBEvent;
import backend.jpa.entities.log.LogEvent;
import backend.jpa.entities.log.LogLogin;
import backend.jpa.entities.log.LogSQLEvent;
import backend.jpa.entities.log.LogLogin.UserSessionEventType;

public class JPAUnitTest extends AbstractUnitTest {

	@Autowired
	private DataSource dataSource;
	
	private final String userName = "testUserName";
	private final String userLogin = "testUserLogin";
	private final String sessionId = "testSessionId";
	private final String userIp = "testUserIp";
	private final String userAgent = "testUserAgent";
	private final String params = "~myParam1~secondParam2~";
	private final String level = "W";
	private final String contractNumber = "contractNumber";
	private final String ullaTaskId = "ullaTaskId";
	private final String key = "dao.task.start";
	private final UserSessionEventType userSessionEventType = UserSessionEventType.LOGIN_SUCCESS;
	private final Long msgTemplate = 1L;
	private final Long fileMethod = 4L;
	private final Long lineNumber = 1L;
	
	@Test
	@Ignore
	public void test() {
		assertNotNull(dataSource);
		
		String persistanceUnitName = getPersistanceUnitName(dataSource);
		Map<String, String> properties = getPropertiesMap(dataSource);
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(persistanceUnitName, properties);
		EntityManager entityManager = emfactory.createEntityManager();
		
		LogDBEvent dbEvent = new LogDBEvent(key, "1", "2", "3");
		dbEvent.setMsgTemplate(msgTemplate);
		dbEvent.setFileMethod(fileMethod);
		dbEvent.setLevel(level);
		dbEvent.setLineNumber(lineNumber);
		dbEvent.setContractNumber(contractNumber);
		dbEvent.setUllaTaskId(ullaTaskId);
		
		LogSQLEvent sqlEvent = new LogSQLEvent("select * from myTable where col1=?", "lala", "siala", "kac");
		sqlEvent.setContractNumber("logSqlEvent");
		sqlEvent.setLevel("W");
		
		LogLogin logLogin = createLogLogin();
		List<LogEvent> list = new ArrayList<>();
		list.add(sqlEvent);
		list.add(dbEvent);
		logLogin.setLogEvents(list);
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(logLogin);
		entityManager.persist(dbEvent);
		entityManager.persist(sqlEvent);
		
		entityManager.refresh(logLogin);
		transaction.commit();
		
		LogEvent storedSqlEvent = entityManager.find(LogSQLEvent.class, sqlEvent.getId());
		LogEvent storedDBEvent = entityManager.find(LogDBEvent.class, dbEvent.getId());
		assertTrue(storedSqlEvent instanceof LogSQLEvent);
		assertTrue(storedDBEvent instanceof LogDBEvent);
		
		assertEquals("start task: \"{}\" login: \"{}\" taskId: {}", storedDBEvent.getMessage());
		assertEquals(sqlEvent.getMessage(), storedSqlEvent.getMessage());
		
		entityManager.close();
		emfactory.close();
	}
	
	private LogLogin createLogLogin() {
		LogLogin newLogLogin = new LogLogin(userName, userLogin, sessionId, userIp, userAgent, userSessionEventType);
		newLogLogin.setMsgTemplate(msgTemplate);
		newLogLogin.setConcatenatedMessageParams(params);
		return newLogLogin;
	}
	
	private Map<String, String> getPropertiesMap(DataSource dataSource){
		org.apache.tomcat.jdbc.pool.DataSource jpaDataSource = (org.apache.tomcat.jdbc.pool.DataSource) dataSource;
		Map<String, String> map = new HashMap<>();
		String dbPassword = jpaDataSource.getDbProperties().getProperty("password");
		map.put("javax.persistence.schema-generation.database.action", "create");
		map.put("javax.persistence.jdbc.driver", jpaDataSource.getDriverClassName());
		map.put("javax.persistence.jdbc.url", jpaDataSource.getUrl());
		map.put("javax.persistence.jdbc.user", jpaDataSource.getUsername());
		map.put("javax.persistence.jdbc.password", dbPassword);
		return map;
    }
	
	private String getPersistanceUnitName(javax.sql.DataSource dataSource){
		org.apache.tomcat.jdbc.pool.DataSource jpaDataSource = (org.apache.tomcat.jdbc.pool.DataSource) dataSource;
		return jpaDataSource.getName();
	}
	
	@Test
	@Ignore
	public void queryTest() throws Exception {
		String persistanceUnitName = getPersistanceUnitName(dataSource);
		Map<String, String> properties = getPropertiesMap(dataSource);
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(persistanceUnitName, properties);
		EntityManager entityManager = emfactory.createEntityManager();
		String jpaql = "Select m from LogLogin m where m.id = 3750731 ";
		Query query = entityManager.createQuery(jpaql);
		LogLogin logLogin = (LogLogin) query.getSingleResult();
		assertNotNull(logLogin);
		entityManager.close();
		emfactory.close();
	}
	
	@Test
	@Ignore
	public void messageFormatterTest() throws Exception {
		String message = "select * from tabela where id = ? and other = ?";
		String[] params = new String[]{"param1", "param2"};
		String out = MessageFormatter.arrayFormat(message.replaceAll("\\?", "{}"), params).getMessage();
		System.out.println(out);
	}
	
	@Test
	@Ignore
	public void query2Test() throws Exception {
		String persistanceUnitName = getPersistanceUnitName(dataSource);
		Map<String, String> properties = getPropertiesMap(dataSource);
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(persistanceUnitName, properties);
		EntityManager entityManager = emfactory.createEntityManager();
		
		LogLogin ll = entityManager.find(LogLogin.class, 3856781L);
		assertNotNull(ll.getLogEvents());
		assertTrue(ll.getLogEvents().size() > 0);
		for(LogEvent logEvent : ll.getLogEvents()){
			assertNotNull(logEvent);
			System.out.println(logEvent.getId()+" "+logEvent.getLogLoginId());
		}
		
		LogEvent le = entityManager.find(LogDBEvent.class, 3857481L);
		assertNotNull(le.getLogLogin());
		
		entityManager.close();
		emfactory.close();
	}
	
	@Test
	@Ignore
	public void query3Test() throws Exception {
		String persistanceUnitName = getPersistanceUnitName(dataSource);
		Map<String, String> properties = getPropertiesMap(dataSource);
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(persistanceUnitName, properties);
		EntityManager entityManager = emfactory.createEntityManager();
		
		LogEvent le = entityManager.find(LogEvent.class, 3895031L);
		assertNotNull(le.getLogLogin());
		assertNotNull(le.getLogLoginId());
		assertEquals(le.getLogLogin().getId(), le.getLogLoginId());
		
		entityManager.close();
		emfactory.close();
	}
}
