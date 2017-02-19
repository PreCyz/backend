package backend.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import backend.AbstractUnitTest;
import backend.dto.BoxDetails;
import backend.exception.DAOException;
import backend.jpa.entities.BoxMessage;
import backend.jpa.entities.BoxMessageResponse;
import backend.service.impl.BoxService;

public class BoxServiceUnitTest extends AbstractUnitTest{

	@Autowired
	private BoxService service;
	
	@Mock private BoxService mockService;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		MockitoAnnotations.initMocks(this);
	}	
	
	@After
	public void tearDown() throws Exception {
		mockService = null;
	}
	
	@Test
	public void givenMocksWhenSendClientQuestionThenReturnString() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setContractNumber("contractNumber");
		boxDetails.setType("type");
		boxDetails.setMessage("message");
		String nowy = new String();
		when(mockService.sendClientQuestion(boxDetails.getContractNumber(), boxDetails)).thenReturn(nowy);
		assertEquals(nowy, mockService.sendClientQuestion(boxDetails.getContractNumber(), boxDetails));
	}
	
	@Test
	public void givenMocksWhenSaveMessageThenReturnLong() {
		BoxDetails boxDetails = new BoxDetails();
		BoxMessage data = new BoxMessage();
		boxDetails.setData(data);
		Long nowy = 0L;
		when(mockService.saveMessage(boxDetails.getContractNumber(), boxDetails)).thenReturn(nowy);
		assertEquals(nowy, mockService.saveMessage(boxDetails.getContractNumber(), boxDetails));
	}
		
	@Test
	public void givenMocksWhenGetSavedMessagesReturnList() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setContractNumber("contractNumber");
		List<BoxMessage> nowy = new ArrayList<>();
		when(mockService.getSavedMessages(boxDetails.getContractNumber(), boxDetails)).thenReturn(nowy);
		assertEquals(nowy, mockService.getSavedMessages(boxDetails.getContractNumber(), boxDetails));
	}
	
	@Test
	public void givenMocksWhenGetSavedMessagesForIdsThenReturnList() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setIds("ids");
		List<BoxMessage> nowy = new ArrayList<>();
		when(mockService.getSavedMessagesForIds(boxDetails)).thenReturn(nowy);
		assertEquals(nowy, mockService.getSavedMessagesForIds(boxDetails));
	}	
	
	@Test
	public void givenMocksWhenGetMessagesResponsesThenReturnList() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setContractNumber("contractNumber");
		List<BoxMessageResponse> nowy = new ArrayList<>();
		when(mockService.getMessagesResponses(boxDetails.getContractNumber(), boxDetails)).thenReturn(nowy);
		assertEquals(nowy, mockService.getMessagesResponses(boxDetails.getContractNumber(), boxDetails));
	}	
	
	@Test
	public void givenMocksWhenGetReadedMessagesThenReturnEmptyList() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setContractNumber("ZY00150894");
		Set<String> nowy = new HashSet<>();
		when(mockService.getReadedMessages(boxDetails.getContractNumber(), boxDetails)).thenReturn(nowy);
		assertEquals(nowy, mockService.getReadedMessages(boxDetails.getContractNumber(), boxDetails));
	}	

	@Test
	public void givenMocksWhenGetLastPartialSurrenderDateThenReturnDate() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setContractNumber("RL00001051");
		Date nowy = new Date();
		when(mockService.getLastPartialSurrenderDate(boxDetails.getContractNumber(), boxDetails)).thenReturn(nowy);
		assertEquals(nowy, mockService.getLastPartialSurrenderDate(boxDetails.getContractNumber(), boxDetails));
	}	

	@Test
	public void givenMocksWhengetPartialSurrenderQuantityFromDateThenReturnShort() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setContractNumber("ZY00150894");
		boxDetails.setLastAnnualDate(new Date());
		short nowy = 0;
		when(mockService.getPartialSurrenderQuantityFromDate(boxDetails.getContractNumber(), boxDetails)).thenReturn(nowy);
		assertEquals(nowy, mockService.getPartialSurrenderQuantityFromDate(boxDetails.getContractNumber(), boxDetails));
	}	
	
	@Test
	public void givenEmptyContractNumbeWhenGetReadedMessagesThenThrowDAOException() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setContractNumber(null);
		try{
			service.getReadedMessages(boxDetails.getContractNumber(), boxDetails);
			fail("Pusty ContractNumber");
		} catch (DAOException ex){
			doAssertions(ex, "contractNumber", service.getClass());
		}	
	}
	
	@Test
	public void givenEmptyContractNumbeWhenGetPartialSurrenderQuantityFromDateThenThrowDAOException() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setContractNumber(null);
		boxDetails.setLastAnnualDate(new Date());
		try{
			service.getPartialSurrenderQuantityFromDate(boxDetails.getContractNumber(), boxDetails);
			fail("Pusty ContractNumber");
		} catch (DAOException ex){
			doAssertions(ex, "contractNumber", service.getClass());
		}	
	}
	
	@Test
	public void givenEmptyIdsWhenGetSavedMessagesForIdsThenThrowDAOException() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setIds(null);
		try{
			service.getSavedMessagesForIds(boxDetails);
			fail("Pusty Ids");
		} catch (DAOException ex){
			doAssertions(ex, "ids", service.getClass());
		}	
	}

	@Test
	public void givenEmptyLastAnnualDateWhenGetPartialSurrenderQuantityFromDateThenThrowDAOException() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setContractNumber("ZY00150894");
		boxDetails.setLastAnnualDate(null);
		try{
			service.getPartialSurrenderQuantityFromDate(boxDetails.getContractNumber(), boxDetails);
			fail("Pusty LastAnnualDate");
		} catch (DAOException ex){
			doAssertions(ex, "lastAnnualDate", service.getClass());
		}	
	}	
	
	@Test
	public void givenEmptyContractNumbeWhenGetSavedMessagesThenThrowDAOException() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setContractNumber(null);
		try{
			service.getSavedMessages(boxDetails.getContractNumber(), boxDetails);
			fail("Pusty ContractNumber");
		} catch (DAOException ex){
			doAssertions(ex, "contractNumber", service.getClass());
		}	
	}
	

	@Test
	public void givenEmptyContractNumberWhenSendClientQuestionThenThrowDAOException() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setContractNumber(null);
		boxDetails.setType("type");
		boxDetails.setMessage("message");
		try{
			service.sendClientQuestion(boxDetails.getContractNumber(), boxDetails);
			fail("Pusty ContractNumber");
		} catch (DAOException ex){
			doAssertions(ex, "contractNumber", service.getClass());
		}	
	}

	@Test
	public void givenEmptyTypeWhenSendClientQuestionThenThrowDAOException() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setContractNumber("contractNumber");
		boxDetails.setType(null);
		boxDetails.setMessage("message");
		try{
			service.sendClientQuestion(boxDetails.getContractNumber(), boxDetails);
			fail("Pusty Type");
		} catch (DAOException ex){
			doAssertions(ex, "type", service.getClass());
		}	
	}

	@Test
	public void givenEmptyMessageWhenSendClientQuestionThenThrowDAOException() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setContractNumber("contractNumber");
		boxDetails.setType("type");
		boxDetails.setMessage(null);
		try{
			service.sendClientQuestion(boxDetails.getContractNumber(), boxDetails);
			fail("Pusty Message");
		} catch (DAOException ex){
			doAssertions(ex, "message", service.getClass());
		}	
	}	
	
	@Test
	public void givenEmptyContractNumberWhenGetMessagesResponsesThenThrowDAOException() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setContractNumber(null);
		try{
			service.getMessagesResponses(boxDetails.getContractNumber(), boxDetails);
			fail("Pusty ContractNumber");
		} catch (DAOException ex){
			doAssertions(ex, "contractNumber", service.getClass());
		}	
	}
	
	@Test
	public void givenEmptyBoxMessageWhenSaveMessageThenThrowDAOException() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setBoxMessage(null);
		try{
			service.saveMessage(boxDetails.getContractNumber(), boxDetails);
			fail("Pusty BoxMessage");
		} catch (DAOException ex){
			doAssertions(ex, "contractNumber", service.getClass());
		}	
	}	
	
	@Test
	public void givenEmptyContractNumbeWhenGetLastPartialSurrenderDateThenThrowDAOException() {
		BoxDetails boxDetails = new BoxDetails();
		boxDetails.setContractNumber(null);
		try{
			service.getLastPartialSurrenderDate(boxDetails.getContractNumber(), boxDetails);
			fail("Pusty ContractNumber");
		} catch (DAOException ex){
			doAssertions(ex, "contractNumber", service.getClass());
		}	
	}	
}
