package backend.integration;

import static backend.AbstractUnitTest.TEST_LOGGING_APPENDER;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import backend.helper.StringHelper;
import backend.servlet.Log4jInitServlet;

public class IntegrationTestLauncher {
	
	public static void main(String[] args) {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/test-spring-config.xml");
			setUpLog4j(context);
			new IntegrationTestRunner(context).run();
		} catch(Exception ex){
			System.err.printf("Error during integral tests. Exception details: %s\n", ex.getMessage());
			System.out.println("INTEGRATION TEST INCOMPLETE !!!");
		}
	}
	
	private static void setUpLog4j(ApplicationContext context) throws Exception {
		String log4jAppender = context.getBean("log4jAppender", String.class);
		String log4jAppenderLEVEL = context.getBean("log4jAppenderLEVEL", String.class);
		Properties prop = new Properties();
		try {
			prop.load(Log4jInitServlet.class.getClassLoader().getResourceAsStream("log4j.properties"));
		} catch (IOException e) {
			return;
		}
		if(StringHelper.notEmpty(log4jAppender) && StringHelper.notEmpty(log4jAppenderLEVEL)) {
			prop.setProperty("log4j.rootLogger", String.format("%s, %s, DATABASE", log4jAppenderLEVEL, log4jAppender));
		} else {
			prop.setProperty("log4j.rootLogger", "INFO, CONSOLE, DATABASE");
		}
		prop.setProperty("log4j.logger.backend", "WARN");
		prop.setProperty("log4j.appender.DATABASE", TEST_LOGGING_APPENDER);
		PropertyConfigurator.configure(prop);
	}
}
