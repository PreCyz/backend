package backend.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import backend.helper.StringHelper;

@Configuration
public class DatabaseConfig {
	
	@Autowired
	private Environment environment;
	
	@Value("${mysqlDataSourceName}")
	private String mysqlDataSourceName;
	
	@Bean
	public boolean generateDdl() {
		return StringHelper.extractBoolean(environment.getProperty("generateDdl"));
	}
	
	@Bean
	public boolean showSql() {
		return StringHelper.extractBoolean(environment.getProperty("showSql"));
	}
	
	@Bean
	@Required
	public DataSource mySqlDataSource() {
		return jndiLookup(mysqlDataSourceName);
	}

	private DataSource jndiLookup(String dataSourceName) {
		DataSource dataSource;
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			dataSource = (DataSource)envContext.lookup(dataSourceName);
			assert dataSource != null;
			System.out.printf("Connection to [%s] is up and running.%n", dataSourceName);
		} catch (Exception ex) {
			dataSource = null;
		}
		return dataSource;
	}
	
}
