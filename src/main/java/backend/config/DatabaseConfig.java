package backend.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
	
	@Value("${mysqlDataSourceName}")
	private String mysqlDataSourceName;
	
	@Bean(name = "mySqlDataSource")
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
