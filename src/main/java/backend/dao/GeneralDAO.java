package backend.dao;

import javax.sql.DataSource;

import backend.initiator.LoggerInitiator;
import backend.jpa.impl.JpaRepository;

public abstract class GeneralDAO extends LoggerInitiator {
	
	protected DataSource dataSource;
	
	protected JpaRepository jpaRepository;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setJpaRepository(JpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}
	
}