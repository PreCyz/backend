package backend.itest;

import static backend.AbstractUnitTest.LOGGING_APPENDER;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import backend.config.TestCoreConfig;
import backend.servlet.Log4jInitServlet;

public class ITLauncher {
	
	public static void main(String[] args) {
		try {
			ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestCoreConfig.class);
			setUpLog4j();
			new ITRunner(applicationContext).run();
			System.out.println("INTEGRATION TEST COMPLETED.");
		} catch(Exception ex){
			System.err.printf("Error during integral tests. Exception details: %s%n", ex.getMessage());
			System.out.println("INTEGRATION TEST STOPED BECAUSE OF ERROR !!!");
		}
	}
	
	public final static void setUpLog4j() throws IOException {
		final String log4jAppenderFILE = "/home/gawa/Workspace/log";
		Properties prop = new Properties();
		prop.load(Log4jInitServlet.class.getClassLoader().getResourceAsStream("log4j.properties"));
		prop.setProperty("log4j.rootLogger", "INFO, CONSOLE");
		prop.setProperty("log4j.appender.LOGFILE.File", log4jAppenderFILE);
		prop.setProperty("log4j.logger.backend", "WARN");
		prop.setProperty("log4j.appender.DATABASE", LOGGING_APPENDER);
		PropertyConfigurator.configure(prop);
	}
}
