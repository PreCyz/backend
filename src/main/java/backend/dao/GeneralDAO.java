package backend.dao;

import javax.sql.DataSource;

import backend.initiator.LoggerInitiator;
import backend.jpa.impl.JpaRepository;

public abstract class GeneralDAO extends LoggerInitiator {
	
	protected DataSource dataSource;
	
	protected String pushApiServerUrl;
	
	protected String restServerUrl;
	
	protected JpaRepository jpaRepository;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setPushApiServerUrl(String pushApiServerUrl) {
		this.pushApiServerUrl = pushApiServerUrl;
	}

	public void setRestServerUrl(String restServerUrl) {
		this.restServerUrl = restServerUrl;
	}

	public void setJpaRepository(JpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}
	
}