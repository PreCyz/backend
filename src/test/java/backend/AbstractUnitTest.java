package backend;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import backend.config.TestCoreConfig;
import backend.exception.ApplicationUncheckedException;
import backend.itest.ITLauncher;
import backend.itest.TestSessionService;
import backend.service.SessionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestCoreConfig.class})
public abstract class AbstractUnitTest {
	
	public static final String LOGGING_APPENDER = "backend.log4j.LoggingAppender";

	protected static final String EXCEPTION_MESSAGE_PREFIX = "backend.service.empty.parameter";

	public static final String CONTRACT_NUMBER = "anyContractNumber";
	
	@Autowired
	protected SessionService sessionService;
	
	@PostConstruct
	public void setUpLog4j() throws IOException {
		ITLauncher.setUpLog4j();
	}
	
	protected void doAssertions(ApplicationUncheckedException ex, String paramName, Class<?> serviceClass) {
		String serviceName = null;
		if(serviceClass != null) {
			serviceName = extractServiceName(serviceClass);
		}
		String errMsg = String.format("%s - empty parameter [%s].", serviceName, paramName);
		assertEquals(errMsg, ex.getMessage());
		assertEquals(EXCEPTION_MESSAGE_PREFIX, ex.getMessageKey());
	}

	private String extractServiceName(Class<?> serviceClass) {
		String serviceName = serviceClass.getSimpleName();
		if(serviceName.contains("$")) {
			return serviceName.substring(0, serviceName.indexOf('$'));
		}
		return serviceName;
	}
	
	public void setUp() throws Exception {
		if (sessionService instanceof TestSessionService) {
			((TestSessionService)sessionService).initService();
		}
	}
}