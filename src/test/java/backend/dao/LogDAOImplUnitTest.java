package backend.dao;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import backend.service.impl.LogService;

public class LogDAOImplUnitTest {
	
	@Mock
	private LogService mockService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
		mockService = null;
	}

	@Test
	@Ignore
	public void testSaveLogEvent() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testSaveEventLogs() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testSaveLogs() {
		fail("Not yet implemented");
	}

}
