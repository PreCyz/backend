package backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import backend.dao.AuthenticationDAO;
import backend.dao.BoxDAO;
import backend.dao.InboxDAO;
import backend.dao.LogDAO;
import backend.dao.impl.AuthenticationDAOImpl;
import backend.dao.impl.BoxDAOImpl;
import backend.dao.impl.InboxDAOImpl;
import backend.dao.impl.LogDAOImpl;
import backend.jpa.impl.JpaRepository;

@Configuration
public class RepositoryConfig {
	
	@Autowired
	private DatabaseConfig databaseConfig;
	
	@Autowired
	private JpaRepository jpaRepository;
	
	@Bean
	public AuthenticationDAO authenticationDao() {
		AuthenticationDAOImpl dao = new AuthenticationDAOImpl();
		dao.setDataSource(databaseConfig.mySqlDataSource());
		return dao;
	}
	
	@Bean
	public BoxDAO boxDao() {
		BoxDAOImpl boxDao = new BoxDAOImpl();
		boxDao.setJpaRepository(jpaRepository);
		return boxDao;
	}
	
	@Bean
	public InboxDAO inboxDao() {
		InboxDAOImpl dao = new InboxDAOImpl();
		dao.setAuthenticationDao(authenticationDao());
		dao.setBoxDao(boxDao());
		return dao;
	}
	
	@Bean
	public LogDAO logDao() {
		LogDAOImpl dao = new LogDAOImpl();
		dao.setJpaRepository(jpaRepository);
		return dao;
	}

}
