package backend.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import backend.jpa.CrudJPA;
import backend.jpa.impl.CrudJPAImpl;

@Configuration
@EnableTransactionManagement
public class JpaConfig {
	
	@Autowired
	private DataSource mySqlDataSource;
	
	@Bean
	@DependsOn(value = "mySqlDataSource")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setJpaVendorAdapter(jpaVendorAdapter());
	    factory.setDataSource(mySqlDataSource);
	    factory.setJpaProperties(jpaProperties());
	    factory.setPackagesToScan("backend");
	    //factory.afterPropertiesSet();
	    return factory;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		EclipseLinkJpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
	    vendorAdapter.setGenerateDdl(true);
	    vendorAdapter.setShowSql(true);
	    vendorAdapter.setDatabasePlatform("org.eclipse.persistence.platform.database.MySQLPlatform");
		return vendorAdapter;
	}
	
	private Properties jpaProperties() {
		Properties jpaProperties = new Properties();
	    jpaProperties.setProperty("eclipselink.weaving", "false");
	    return jpaProperties;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
	@Bean
	public CrudJPA crudJpa() {
		return new CrudJPAImpl();
	}

}
