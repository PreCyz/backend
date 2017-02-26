package backend.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import backend.dao.AuthenticationDAO;
import backend.dao.BoxDAO;
import backend.dao.LogDAO;
import backend.dao.impl.AuthenticationDAOImpl;
import backend.dao.impl.BoxDAOImpl;
import backend.dao.impl.LogDAOImpl;
import backend.jpa.CrudJPA;
import backend.jpa.impl.JpaRepository;

@Configuration
public class RepositoryConfig {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private DatabaseConfig databaseConfig;
	
	@Autowired
	private CrudJPA crudJpa;
	
	@Bean
	public AuthenticationDAO authenticationDao() {
		AuthenticationDAOImpl dao = new AuthenticationDAOImpl();
		dao.setDataSource(databaseConfig.mySqlDataSource());
		return dao;
	}
	
	@Bean
	public BoxDAO boxDao() {
		BoxDAOImpl boxDao = new BoxDAOImpl();
		boxDao.setJpaRepository(jpaRepository());
		return boxDao;
	}
	
	@Bean
	public LogDAO logDao() {
		LogDAOImpl dao = new LogDAOImpl();
		dao.setJpaRepository(jpaRepository());
		return dao;
	}
	
	@Bean
	public JpaRepository jpaRepository() {
		return new JpaRepository(entityManager, crudJpa); 
	};

}
