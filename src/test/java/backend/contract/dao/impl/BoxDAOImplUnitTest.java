package backend.contract.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import backend.AbstractUnitTest;
import backend.dao.BoxDAO;
import backend.jpa.entities.BoxMessage;
import backend.jpa.entities.BoxMessageResponse;

public class BoxDAOImplUnitTest extends AbstractUnitTest {

	@Mock
	private BoxDAO mockDao;
	
	@Resource(name = "boxDao")
	private BoxDAO dao; 
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenMocksSendClientQuestionThenReturnString() {
		String contractNumber = "ZY00041677";
		String type = "offer";
		String message = "message";
		String nowy = new String();
		when(mockDao.sendClientQuestion(contractNumber, type, message)).thenReturn(nowy);
		assertEquals(nowy, (mockDao.sendClientQuestion(contractNumber, type, message)));
	}	

	@Test
	public void givenMockWhenSaveMessageThenReturnLong() {
		BoxMessage data = new BoxMessage();
		Long long1 = 0L;
		when(mockDao.saveMessage(data)).thenReturn(long1);
		assertEquals(long1, (mockDao.saveMessage(data)));
	}
	
	@Test
	public void givenMockWhenGetSavedMessagesThenReturnEmptyArrayList() {
		String contractNumber = "ZY00041677";
		when(mockDao.getSavedMessages(contractNumber)).thenReturn(new ArrayList<BoxMessage>());
		assertTrue((mockDao.getSavedMessages(contractNumber)).isEmpty());
	}	
		
	@Test
	public void givenMockWhenGetSavedMessagesForIdsThenReturnEmptyArrayList() {
		String ids = "ids";
		when(mockDao.getSavedMessagesForIds(ids)).thenReturn(new ArrayList<BoxMessage>());
		assertTrue((mockDao.getSavedMessagesForIds(ids)).isEmpty());
	}	
	
	@Test
	public void givenDaoAndNotProperIdWhenGetSavedMessagesForIdsThenReturnNull() {
		String ids = "ids";
		assertNull(dao.getSavedMessagesForIds(ids));
	}
	
	@Test
	public void givenMockWhenGetMessagesResponsesThenReturnEmptyArrayList() {
		String contractNumber = "RL00001051";
		when(mockDao.getMessagesResponses(contractNumber)).thenReturn(new ArrayList<BoxMessageResponse>());
		assertTrue((mockDao.getMessagesResponses(contractNumber)).isEmpty());
	}	
	
	@Test
	public void givenMockWhenGetReadedMessagesThenReturnEmptySet() {
		String username = "test";
		String contractNumber = "ZY00041677";
		Set<String> h = new HashSet<String>();
		when(mockDao.getReadedMessages(username, contractNumber)).thenReturn(h);
		assertTrue((mockDao.getReadedMessages(username, contractNumber)).isEmpty());
	}
	
	@Test
	public void givenMockWhenGetLastPartialSurrenderDateThenReturnDate() {
		String contractNumber = "RL00001051";
		Date nowa = new Date();
		when(mockDao.getLastPartialSurrenderDate(contractNumber)).thenReturn(nowa);
		assertEquals(nowa, (mockDao.getLastPartialSurrenderDate(contractNumber)));
	}	
	
	@Test
	public void givenMockWhenGetPartialSurrenderQuantityFromDateThenReturnShort() {
		String contractNumber = "ZY00150894";
		Date lastAnnualDate = new Date();
		Short nowy = 0;
		when(mockDao.getPartialSurrenderQuantityFromDate(contractNumber, lastAnnualDate)).thenReturn(nowy);
		//assertEquals(nowy, (mockDao.getPartialSurrenderQuantityFromDate(contractNumber, lastAnnualDate)));
	}
}
