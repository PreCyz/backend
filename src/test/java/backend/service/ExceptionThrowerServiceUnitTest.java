package backend.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import backend.AbstractUnitTest;
import backend.exception.DAOException;
import backend.service.impl.ExceptionThrowerService;

public class ExceptionThrowerServiceUnitTest extends AbstractUnitTest {

	private static final String EXCEPTION_MESSAGE_PREFIX = "backend.service.empty.parameter";
	@Mock private Log logger;
	private String serviceName = "SERVICE NAME NOT SET!!!";
	
	@Autowired
	private ExceptionThrowerService thrower;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		thrower.setLogger(logger);
		thrower.setServiceName(serviceName);
	}
	
	@After
	public void tearDown() {
		thrower.setLogger(null);
		thrower.setServiceName(null);
	}
	
	@Test
	public void givenNullWhenThrowNullOrEmptyThenThrowDAOException() {
		String paramName = "contractNumber";
		String errorInfo = String.format("%s - empty parameter [%s].", serviceName, paramName);
		doNothing().when(logger).info(errorInfo);
		
		try {
			thrower.throwNullOrEmpty(null, paramName);
			fail("Should be DAOException");
		} catch (DAOException ex) {
			assertThat(ex, is( notNullValue() ));
			assertThat(ex.getMessage(), is( equalTo(errorInfo) ));
			assertThat(ex.isNotLog(), is( equalTo(true) ));
			assertThat(ex.getMessageKey(), is( equalTo(EXCEPTION_MESSAGE_PREFIX) ));
			assertThat(ex.getMessageParams(), is( equalTo(new String[]{serviceName, paramName})));
			verify(logger, times(1)).error(errorInfo);
		}
	}
	
	@Test
	public void givenEmptyStringWhenThrowEmptyStringThenThrowDAOException() {
		String paramName = "contractNumber";
		String errorInfo = String.format("%s - empty parameter [%s].", serviceName, paramName);
		doNothing().when(logger).info(errorInfo);
		
		try {
			thrower.throwNullOrEmpty("", paramName);
			fail("Should be DAOException");
		} catch (DAOException ex) {
			assertThat(ex, is( notNullValue() ));
			assertThat(ex.getMessage(), is( equalTo(errorInfo) ));
			assertThat(ex.isNotLog(), is( equalTo(true) ));
			assertThat(ex.getMessageKey(), is( equalTo(EXCEPTION_MESSAGE_PREFIX) ));
			assertThat(ex.getMessageParams(), is( equalTo(new String[]{serviceName, paramName})));
			verify(logger, times(1)).error(errorInfo);
		}
	}
	
	@Test
	public void givenNullParamNameWhenThrowEmptyStringThenThrowNullPointerException() {
		try {
			thrower.throwNullOrEmpty(null, null);
			fail("Should be DAOException");
		} catch (DAOException ex) {
			assertThat(ex, is( notNullValue() ));
			assertThat(ex.getMessageKey(), is( equalTo(EXCEPTION_MESSAGE_PREFIX) ));
			assertThat(ex.getMessageParams(), is( equalTo(new String[]{serviceName, null})));
		}
	}
	
	@Test
	public void givenNotEmptyParamNameAndValueWhenThrowEmptyStringThenDoNothing() {
		thrower.throwNullOrEmpty(new BigDecimal(1), "share");
		verify(logger, times(0)).info("");
	}
}
