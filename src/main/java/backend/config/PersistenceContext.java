package backend.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import backend.jpa.CrudJPA;
import backend.jpa.impl.CrudJPAImpl;
import backend.jpa.impl.JpaRepository;

@Configuration
@EnableTransactionManagement
public class PersistenceContext {
	
	@Autowired
	private DataSource mySqlDataSource;
	
	@Bean
	@DependsOn(value = "mySqlDataSource")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		EclipseLinkJpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
	    vendorAdapter.setGenerateDdl(true);
	    vendorAdapter.setShowSql(true);
	    vendorAdapter.setDatabasePlatform("org.eclipse.persistence.platform.database.MySQLPlatform");

	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setJpaVendorAdapter(vendorAdapter);
	    factory.setPackagesToScan("backend");
	    factory.setDataSource(mySqlDataSource);
	    factory.setJpaProperties(jpaProperties());
	    factory.afterPropertiesSet();

	    return factory;
	  }
	
	private Properties jpaProperties() {
		Properties jpaProperties = new Properties();
	    jpaProperties.setProperty("eclipselink.weaving", "false");
	    return jpaProperties;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(entityManagerFactory);
	    return txManager;
	}
	
	@Bean
	public JpaRepository jpaRepository(EntityManagerFactory entityManagerFactory) {
		return new JpaRepository(entityManagerFactory.createEntityManager(), crudJpa()); 
	};
	
	@Bean
	public CrudJPA crudJpa() {
		return new CrudJPAImpl();
	}

}
