package backend.itest.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.sql.DataSource;

import backend.jpa.JPAUnitTest;

public class JpaIntegrationTest extends AbstractIT {
	
	private DataSource dataSource;
	
	public JpaIntegrationTest(DataSource dataSource) {
		this.dataSource = dataSource; 
	}

	@Override
	protected void executeTests() throws Exception {
		List<Class<?>> entities = getAllEntities();
        for(Class<?> entity : entities){
        	givenDataSourceWhenCountRecordsThenReturnActualNumberOfRecords(entity);
        }
	}

	private List<Class<?>> getAllEntities() throws IOException, ClassNotFoundException {		
		final String packageName = "backend/jpa/entities";
		ClassLoader cl = JPAUnitTest.class.getClassLoader();
		
        String dottedPackage = packageName.replaceAll("[/]", ".");
        List<Class<?>> classes = new LinkedList<>();
        URL upackage = cl.getResource(packageName);

        BufferedReader d = new BufferedReader(new InputStreamReader((InputStream) upackage.getContent()));
        String line = null;
        while ((line = d.readLine()) != null) {
            if (line.endsWith(".class")) {
               classes.add(Class.forName(dottedPackage+"."+line.substring(0,line.lastIndexOf('.'))));
            }
        }
		return classes;
	}
	
	private void givenDataSourceWhenCountRecordsThenReturnActualNumberOfRecords(Class<?> type) throws Exception {
		displayTestName();
		Long numberOfRecords = countRecords(type);
		String msg = String.format("Aktualnie w bazie danych jest %d rekordów %s.", numberOfRecords, 
				type.getSimpleName());
		assertion(msg, numberOfRecords >= 0);
	}
	
	private Long countRecords(Class<?> entity) {
		String persistanceUnitName = getPersistanceUnitName(dataSource);
		Map<String, String> properties = getPropertiesMap(dataSource);
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(persistanceUnitName, properties);
		EntityManager entitymanager = emfactory.createEntityManager();
		String jpaql = String.format("Select count(m) from %s m ", entity.getSimpleName());
		Query query = entitymanager.createQuery(jpaql);
		return (Long)query.getSingleResult();
	}
	
	private Map<String, String> getPropertiesMap(DataSource dataSource) {
		org.apache.tomcat.jdbc.pool.DataSource jpaDataSource = (org.apache.tomcat.jdbc.pool.DataSource) dataSource;
		Map<String, String> map = new HashMap<>();
		String dbPassword = jpaDataSource.getDbProperties().getProperty("password");
		map.put("javax.persistence.schema-generation.database.action", "create");
		map.put("javax.persistence.jdbc.driver", jpaDataSource.getDriverClassName());
		map.put("javax.persistence.jdbc.url", jpaDataSource.getUrl());
		map.put("javax.persistence.jdbc.user", jpaDataSource.getUsername());
		map.put("javax.persistence.jdbc.password", dbPassword);
		return map;
    }
	
	private String getPersistanceUnitName(javax.sql.DataSource dataSource) {
		org.apache.tomcat.jdbc.pool.DataSource jpaDataSource = (org.apache.tomcat.jdbc.pool.DataSource) dataSource;
		return jpaDataSource.getName();
	}

	@Override
	protected void setup() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void cleanup() {
		// TODO Auto-generated method stub
		
	}
}
