package backend.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import backend.monitor.MySqlMonitor;
import backend.monitor.SubsystemMonitor;
import backend.monitor.executor.MonitorExecutor;
import backend.service.EmailService;
import backend.service.impl.EmailServiceImpl;
import backend.service.impl.MonitorSchedule;
import backend.util.helper.StringHelper;

@EnableScheduling
@Configuration
public class ScheduleConfig {
	
	@Autowired
	private Environment environment;
	
	@Resource(name = "mySqlDataSource")
	private DataSource mysqlDataSource;
	
	@Bean
	public boolean enableMySqlMonitor() {
		return StringHelper.extractBoolean(environment.getProperty("enableMySqlMonitor"));
	}
	
	@Bean
	public boolean enableMonitor() {
		return StringHelper.extractBoolean(environment.getProperty("enableMonitor"));
	}
	
	//Second method to get parameter from web.xml or tomcat context.xml file
	/*@org.springframework.beans.factory.annotation.Value("${enableMonitor}")
	private String enableMonitor;*/
	
	@Bean
	public SubsystemMonitor mysqlMonitor() {
		SubsystemMonitor mysqlMonitoring = new MySqlMonitor(enableMySqlMonitor(), mysqlDataSource);
		return mysqlMonitoring;
	}
	
	@Bean
	public String emailSmtpHost() {
		return environment.getProperty("emailSmtpHost");
	}
	
	@Bean 
	public String emailDefaultFrom() {
		return environment.getProperty("emailDefaultFrom");
	}
	
	@Bean
	public EmailService emailService() {
		return new EmailServiceImpl(emailSmtpHost(), emailDefaultFrom());
	}
	
	@Bean 
	public String sendEmailErrors() {
		return environment.getProperty("sendEmailErrors");
	}
	
	@Bean 
	public String monitorEmailRecivers() {
		return environment.getProperty("monitorEmailRecivers");
	}
	
	@Bean
	public MonitorExecutor monitorExecutor() {
		return new MonitorExecutor(enableMonitor());
	}
	
	@Bean
	public MonitorSchedule monitorService() {
		MonitorSchedule monitorSchedule = new MonitorSchedule(monitorExecutor());
		monitorSchedule.setMysqlMonitor(mysqlMonitor());
		return monitorSchedule;
	}

}
