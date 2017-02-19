package backend.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import backend.AbstractUnitTest;
import backend.dto.BoxMessages;
import backend.dto.InboxDetails;
import backend.exception.DAOException;
import backend.jpa.entities.BoxMessage;
import backend.service.impl.InboxService;

public class InboxServiceUnitTest extends AbstractUnitTest {
	
	@Autowired
	private InboxService service;
	
	@Mock 
	private InboxService mockService;
	
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
	public void givenMocksWhenGetMessagesThenReturnBoxMessages() {
		List<BoxMessage> groupedMessages = null;
		List<BoxMessage> messages = null;
		InboxDetails inboxDetails = new InboxDetails();
		inboxDetails.setSecondaryUsername("1234");
		inboxDetails.setContractNumber(CONTRACT_NUMBER);
		inboxDetails.setUserContractRole("1234");
		when(mockService.getMessages(inboxDetails.getContractNumber(), inboxDetails)).thenReturn(new BoxMessages(messages, groupedMessages));
		assertNotNull(mockService.getMessages(inboxDetails.getContractNumber(), inboxDetails));
	}
	
	@Test
	public void givenMocksWhenGetMessageDatailsThenReturnList() {
		InboxDetails inboxDetails = new InboxDetails();
		BoxMessage boxMessage = new BoxMessage();
		inboxDetails.setBoxMessage(boxMessage);
		List<BoxMessage> boxMessages = new ArrayList<BoxMessage>();
		when(mockService.getMessageDatails(inboxDetails.getContractNumber(), inboxDetails)).thenReturn(boxMessages);
		assertNotNull(mockService.getMessageDatails(inboxDetails.getContractNumber(), inboxDetails));
	}
	
	@Test
	public void givenNullSecondaryUsernameWhenGetMessagesThenReturnException(){
		InboxDetails inboxDetails = new InboxDetails();
		inboxDetails.setSecondaryUsername(null);
		inboxDetails.setContractNumber(CONTRACT_NUMBER);
		try{
			service.getMessages(inboxDetails.getContractNumber(), inboxDetails);
			fail("Null userContractRole");
		} catch (DAOException ex){
			doAssertions(ex, "userContractRole", service.getClass());
		}
	}	
		
	@Test
	public void givenNullContractNumberWhenGetMessagesThenReturnException(){
		InboxDetails inboxDetails = new InboxDetails();
		inboxDetails.setSecondaryUsername("TEST");
		inboxDetails.setContractNumber(null);
		try {
			service.getMessages(inboxDetails.getContractNumber(), inboxDetails);
			fail("Null ContractNumber");
		} catch (DAOException ex) {
			doAssertions(ex, "contractNumber", service.getClass());
		}
	}			

	@Test
	public void givenNullUserContractRoleWhenGetMessagesThenReturnException(){
		InboxDetails inboxDetails = new InboxDetails();
		inboxDetails.setSecondaryUsername("TEST");
		inboxDetails.setContractNumber(CONTRACT_NUMBER);
		inboxDetails.setUserContractRole(null);
		try {
			service.getMessages(inboxDetails.getContractNumber(), inboxDetails);
			fail("Null userContractRole");
		} catch (DAOException ex) {
			doAssertions(ex, "userContractRole", service.getClass());
		}
	}	

	@Test
	public void givenNullContractsBeanWhenGetMessagesThenReturnException(){
		InboxDetails inboxDetails = new InboxDetails();
		inboxDetails.setSecondaryUsername("TEST");
		inboxDetails.setContractNumber(CONTRACT_NUMBER);
		inboxDetails.setUserContractRole("12345");
		try {
			service.getMessages(inboxDetails.getContractNumber(), inboxDetails);
			fail("Null contractsBean");
		} catch (DAOException ex) {
			doAssertions(ex, "contractsBean", service.getClass());
		}
	}	
	
}
