package backend.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import backend.AbstractUnitTest;
import backend.dto.LoggedUser;
import backend.dto.LoginDetails;
import backend.facade.impl.RestFacadeImpl;

public class RestFacadeUnitTest extends AbstractUnitTest{

	@Autowired
	private RestFacadeImpl restFacade;
	
	@Mock
	private RestFacadeImpl mockRestFacade;
	private LoggedUser user;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		user = new LoggedUser();
		user.setUserLogin("gawa");
	}

	@After
	public void tearDown() throws Exception {
		mockRestFacade = null;
		user = null;
	}

	@Test
	public void givenMocksWhenGetLoggedUserReturnUser() throws Exception {
		LoginDetails ld = new LoginDetails();
		when(mockRestFacade.login(ld)).thenReturn(user);
		assertEquals("gawa", mockRestFacade.login(ld).getUserLogin());
	}

	@Test
	public void givenAutowiredFieldWhenTestThenFieldIsNotNull() throws Exception {
		assertNotNull(restFacade);
	}
}
