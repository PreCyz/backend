package backend.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration(value = "databaseConfig")
public class TestDatabaseConfig extends DatabaseConfig {
	
	@Override
	@Bean 
	public boolean generateDdl() {
		return true;
	}
	
	@Override
	@Bean
	public boolean showSql() {
		return false;
	}
	
	@Override
	@Bean
	@Required
	public DataSource mySqlDataSource() {
		Properties connectionProperties = connectionProperties();
		String username = connectionProperties.getProperty("username");
		String password = connectionProperties.getProperty("password");
		String url = connectionProperties.getProperty("url");
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(url, username, password);
		driverManagerDataSource.setConnectionProperties(connectionProperties);
		return driverManagerDataSource;
	}
	
	private Properties connectionProperties() {
		try (InputStream resourceAsStream =TestCoreConfig.class.getClassLoader()
				.getResourceAsStream("testDatabase.properties")){
			Properties connectionProperties = new Properties();
			connectionProperties.load(resourceAsStream);
			return connectionProperties;
		} catch (IOException e) {
			System.err.printf("Can not load database properties. Details: %s.%n", e.getMessage());
		}
		return null;
	}

}
