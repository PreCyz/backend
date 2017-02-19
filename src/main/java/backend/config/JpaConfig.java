package backend.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import backend.jpa.CrudJPA;
import backend.jpa.impl.CrudJPAImpl;
import backend.jpa.impl.JpaRepository;

@Configuration
@EnableTransactionManagement
public class JpaConfig {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private DatabaseConfig databaseConfig;

	@Bean
	public EntityManagerFactory entityManagerFactory() {

		EclipseLinkJpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
	    vendorAdapter.setGenerateDdl(false);
	    vendorAdapter.setShowSql(false);
	    vendorAdapter.setDatabasePlatform("org.eclipse.persistence.platform.database.OraclePlatform");

	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setJpaVendorAdapter(vendorAdapter);
	    factory.setPackagesToScan("com.acme.domain");
	    factory.setDataSource(databaseConfig.mySqlDataSource());
	    factory.afterPropertiesSet();

	    return factory.getObject();
	  }

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(entityManagerFactory());
	    return txManager;
	}
	
	@Bean
	public JpaRepository jpaRepository() {
		return new JpaRepository(entityManager, crudJpa()); 
	};
	
	@Bean
	public CrudJPA crudJpa() {
		return new CrudJPAImpl();
	}

}
