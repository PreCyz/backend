package backend.jpa.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import backend.jpa.AutoIncrementEntry;
import backend.jpa.CrudJPA;
import backend.jpa.Entry;

public class JpaRepository {

	private final EntityManager entityManager;
	
	private final CrudJPA crudJpa;
	
	public JpaRepository(EntityManager entityManager, CrudJPA crudJpa) {
		this.entityManager = entityManager;
		this.crudJpa = crudJpa;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(Entry entity) {
		Entry existingEntity = null;
		if (entity.getId() != null) {
			existingEntity = find(entity);
		}
		if (existingEntity == null) {
			insert(entity);
		} else {
			existingEntity.update(entity);
		}
	}
	
	public Entry find(Entry entity) {
		return find(entity, entity.getId());
	}
	
	public <T> T find(T entity, Object entityId) {
		if (entity != null && entityId != null) {
			return crudJpa.find(entityManager, entity, entityId);
		}
		return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void insert(Object entity){
		crudJpa.save(entityManager, entity);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveCollection(Collection<AutoIncrementEntry> collection) {
		collection.stream().forEach(log -> save(log));
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(AutoIncrementEntry entity) {
		delete(entity, entity.getId());
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public <T> void delete(T entity, Object id) {
		if (entity != null && id != null) {
			T existingEntity = find(entity, id);
			crudJpa.delete(entityManager, existingEntity);
		}
	}

	public Collection<? extends AutoIncrementEntry> loadAll(Class<? extends AutoIncrementEntry> type) {
		return crudJpa.loadAll(entityManager, type);
	}

	public <A> List<A> findListWithNamedQuery(Class<A> type, String queryName, Map<String, String> params) {
		return crudJpa.getListFromNamedQuery(entityManager, type, queryName, params);
	}

	public CriteriaBuilder criteriaBuilder() {
		return entityManager.getCriteriaBuilder();
	}
	
	public <A> Query createQuery(CriteriaQuery<A> criteriaQuery) {
		return entityManager.createQuery(criteriaQuery);
	}
	
	public Query createQuery(String sqlQuery) {
		return entityManager.createQuery(sqlQuery);
	}
	
	public Query createNativeQuery(String sqlQuery) {
		return entityManager.createNativeQuery(sqlQuery);
	}
	
	public Query createNamedQuery(String queryName) {
		return entityManager.createNamedQuery(queryName);
	}
}
