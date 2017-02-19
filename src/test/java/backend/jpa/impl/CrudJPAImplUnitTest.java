package backend.jpa.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Collections;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import backend.AbstractUnitTest;
import backend.jpa.CrudJPA;
import backend.jpa.entities.log.LogLogin;
import backend.jpa.entities.log.LogSequenceEntry;

public class CrudJPAImplUnitTest extends AbstractUnitTest {
	
	@Mock
	private CrudJPA mockCrudJpa;
	private EntityManager entityManager;
	private LogSequenceEntry entity;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		entity = new LogLogin();
	}

	@After
	public void tearDown() throws Exception {
		mockCrudJpa = null;
		entity = null;
		entityManager = null;
	}

	@Test
	public void givenMockDaoNullEntryJPAWhenLoadThenReturnRecord() {
		when(mockCrudJpa.find(entityManager, null)).thenReturn(entity);
		assertNotNull(mockCrudJpa.find(entityManager, null));
	}

	@Test
	public void givenMockDaoAndRecordIdWhenDeleteThenDoNothing() {
		doNothing().when(mockCrudJpa).delete(entityManager, entity);
		mockCrudJpa.delete(entityManager, entity);
	}
	
	@Test
	public void givenMockDaoAndLogLoginEntryWhenLoadAllReturnCollectionOfLogLogin() throws Exception {
		when(mockCrudJpa.loadAll(entityManager, LogLogin.class)).thenReturn(Collections.emptyList());
		assertTrue(mockCrudJpa.loadAll(entityManager, LogLogin.class).isEmpty());
	}
	
	@Test
	public void givenMockDaoAndSomeEntityWhenSaveThenDoNothing() {
		Object entity = new Object();
		doNothing().when(mockCrudJpa).save(entityManager, entity);
		mockCrudJpa.save(entityManager, entity);
	}
}
