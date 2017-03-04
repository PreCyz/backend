package backend.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import javax.xml.ws.WebServiceException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import backend.dto.LoggedUser;
import backend.service.SessionService;

public class SessionServiceUnitTest {

	@Mock 
	private SessionService mockService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
		mockService = null;
	}

	@Test
	public void givenMocksWhenAddToSessionThenDoNothing() {
		doNothing().when(mockService).addToSession("", "");
		mockService.addToSession("", "");
	}
	
	@Test(expected = WebServiceException.class)
	public void givenMocksWhenAddToSessionThenThrowWebServiceException() {
		doThrow(WebServiceException.class).when(mockService).addToSession("", "");
		mockService.addToSession("", "");
	}

	@Test
	public void givenMocksWhenGetFromSessionThenReturnLoggedUser() {
		doReturn(new LoggedUser()).when(mockService).getFromSession("", LoggedUser.class);
		assertNotNull(mockService.getFromSession("", LoggedUser.class));
	}
	
	@Test(expected = WebServiceException.class)
	public void givenMocksWhenGetFromSessionThenThrowWebServiceException() {
		doThrow(WebServiceException.class).when(mockService).getFromSession("", Object.class);
		mockService.getFromSession("", Object.class);
	}

	@Test
	public void givenMocksWhenGetSessionIdThenReturnSessionId() {
		doReturn("sessionId").when(mockService).getSessionId();
		assertNotNull(mockService.getSessionId());
	}
	
	@Test
	public void givenMocksWhenGetUserFromSessionThenReturnLoggeUser() throws Exception {
		doReturn(new LoggedUser()).when(mockService).getUserWithActualSession();
		assertNotNull(mockService.getUserWithActualSession());
	}
	
	@Test(expected = WebServiceException.class)
	public void givenMocksWhenGetUserFromSessionThenThrowWebServiceException() {
		doThrow(WebServiceException.class).when(mockService).getUserWithActualSession();
		mockService.getUserWithActualSession();
	}

}
