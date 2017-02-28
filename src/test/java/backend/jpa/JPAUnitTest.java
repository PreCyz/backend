package backend.jpa;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import backend.AbstractUnitTest;

public class JPAUnitTest extends AbstractUnitTest {
	
	@Autowired
	private DataSource mySqlDataSource;
	
	private DriverManagerDataSource driverManagerDataSource;

	@Before
	public void setUp() throws Exception {
		driverManagerDataSource = (DriverManagerDataSource) mySqlDataSource;
	}

	@After
	public void tearDown() throws Exception {
		driverManagerDataSource = null;
	}
	
	@Test
	public void givenDefinedDataSourceWhenAutowiredThenSetDataSource() {
		assertNotNull(mySqlDataSource);
		assertNotNull("URL for data source should not be null.", driverManagerDataSource.getUrl());
		assertNotNull("Username should not be null.", driverManagerDataSource.getUsername());
		assertNotNull("Password should not be null.", driverManagerDataSource.getPassword());
		assertNotNull("Data source properties should not be null.", 
				driverManagerDataSource.getConnectionProperties());
		assertTrue("Data source should have some properties", 
				driverManagerDataSource.getConnectionProperties().keySet().size() > 0);
	}
	
}
