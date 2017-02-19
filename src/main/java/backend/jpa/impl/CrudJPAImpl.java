package backend.jpa.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import backend.jpa.CrudJPA;
import backend.jpa.Entry;
import backend.jpa.entities.log.LogSequenceEntry;

public class CrudJPAImpl implements CrudJPA {

	@Override
	public void delete(EntityManager entityManager, Object entity) {
		entityManager.remove(entity);
	}

	@Override
	public Collection<? extends LogSequenceEntry> loadAll(EntityManager entityManager,
			Class<? extends LogSequenceEntry> type) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<? extends LogSequenceEntry> from = criteriaQuery.from(type);

		CriteriaQuery<Object> select = criteriaQuery.select(from);
		TypedQuery<Object> typedQuery = entityManager.createQuery(select);
		Collection<Object> objectCollection = typedQuery.getResultList();

		Collection<LogSequenceEntry> resultCollection = new LinkedList<>();
		objectCollection.forEach(obj -> resultCollection.add((LogSequenceEntry) obj));

		return resultCollection;
	}

	@Override
	public Entry find(EntityManager entityManager, Entry entity) {
		return find(entityManager, entity, entity.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T find(EntityManager entityManager, T entity, Object id) {
		return (T) entityManager.find(entity.getClass(), id);
	}
	
	@Override
	public <T> T merge(EntityManager entityManager, T type) {
		return entityManager.merge(type);
	}

	@Override
	public void refresh(EntityManager entityManager, Object entity) {
		entityManager.refresh(entity);
	}
	
	@Override
	public void save(EntityManager entityManager, Object entity) {
		entityManager.persist(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A> List<A> getListFromNamedQuery(EntityManager entityManager, Class<A> type, String queryName, Map<String, String> params) {
		Query query = entityManager.createNamedQuery(queryName);
		for(String key : params.keySet()){
			query.setParameter(key, params.get(key));
		}
		List<A> result = query.getResultList();
		return result;
	}

}
