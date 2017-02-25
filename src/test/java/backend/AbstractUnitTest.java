package backend;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.PropertyConfigurator;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import backend.exception.DAOException;
import backend.helper.StringHelper;
import backend.itest.TestSessionService;
import backend.service.SessionService;
import backend.servlet.Log4jInitServlet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/test-spring-config.xml")
public abstract class AbstractUnitTest {
	
	public static final String TEST_LOGGING_APPENDER = "backend.common.log4j.TestLoggingAppender";

	protected static final String EXCEPTION_MESSAGE_PREFIX = "backend.service.empty.parameter";

	public static final String CONTRACT_NUMBER = "anyContractNumber";
	
	@Resource(name="log4jAppender")
	private String log4jAppender;
	@Resource(name="log4jAppenderLEVEL")
	private String log4jAppenderLEVEL;
	@Autowired
	protected SessionService sessionService;
	
	@PostConstruct
	public void setUpLog4j() throws Exception{
		Properties prop = new Properties();
		try {
			prop.load(Log4jInitServlet.class.getClassLoader().getResourceAsStream("log4j.properties"));
		} catch (IOException e) {
			return;
		}
		if(StringHelper.notEmpty(log4jAppender) && StringHelper.notEmpty(log4jAppenderLEVEL)){
			prop.setProperty("log4j.rootLogger", String.format("%s, %s", log4jAppenderLEVEL, log4jAppender));
		} else {
			prop.setProperty("log4j.rootLogger", "INFO, CONSOLE");
		}
		//prop.setProperty("log4j.rootLogger", "INFO, DATABASE");
		prop.setProperty("log4j.logger.backend", "WARN");
		prop.setProperty("log4j.appender.DATABASE", TEST_LOGGING_APPENDER);
		PropertyConfigurator.configure(prop);
	}
	
	protected void doAssertions(DAOException ex, String paramName, Class<?> serviceClass) {
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