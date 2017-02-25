package backend.jpa;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

public interface CrudJPA {
	
	Collection<? extends AutoIncrementEntry> loadAll(EntityManager entityManager, Class<? extends AutoIncrementEntry> type);
	
	AutoIncrementEntry find(EntityManager entityManager, AutoIncrementEntry entity);
	<T> T find(EntityManager entityManager, T type, Object id);
	
	<T> T merge(EntityManager entityManager, T type);
	
	void refresh(EntityManager entityManager, Object entity);
	
	void save(EntityManager entityManager, Object entity);
	
	<A> List<A> getListFromNamedQuery(EntityManager entityManager, Class<A> type, String queryName, Map<String, String> params);
	
	void delete(EntityManager entityManager, Object entity);
}
