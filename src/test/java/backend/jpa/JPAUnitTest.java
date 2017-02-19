package backend.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import backend.AbstractUnitTest;

public class JPAUnitTest extends AbstractUnitTest {
	
	@Autowired
	@Qualifier(value = "dataSource")
	private DataSource dataSource;
	
	private org.apache.tomcat.jdbc.pool.DataSource oracleDataSource;

	@Before
	public void setUp() throws Exception {
		oracleDataSource = (org.apache.tomcat.jdbc.pool.DataSource) dataSource;
	}

	@After
	public void tearDown() throws Exception {
		oracleDataSource = null;
	}
	
	@Test
	public void givenDefinedDataSourceJavaxSqlWhenAutowiredThenSetDataSource() {
		assertNotNull(dataSource);
		assertEquals("jdbc:oracle:thin:@plwadbt1:1521:plwadbt1", oracleDataSource.getUrl());
		assertEquals("stag_pl_mysimon", oracleDataSource.getUsername());
		assertEquals("oracle.jdbc.driver.OracleDriver", oracleDataSource.getDriverClassName());
		assertEquals("jdbc/ploracle-ds", oracleDataSource.getName());
		assertEquals("qq", oracleDataSource.getDbProperties().getProperty("password"));
	}

	@Test
	public void givenDataSourceWhenAutoWiredThenReturnProperProperties() {
		assertNotNull(oracleDataSource);
		assertEquals("jdbc:oracle:thin:@plwadbt1:1521:plwadbt1", oracleDataSource.getUrl());
		assertEquals("stag_pl_mysimon", oracleDataSource.getUsername());
		assertEquals("oracle.jdbc.driver.OracleDriver", oracleDataSource.getDriverClassName());
		assertEquals("jdbc/ploracle-ds", oracleDataSource.getName());
		assertEquals("qq", oracleDataSource.getDbProperties().getProperty("password"));
	}
}
