package backend.dao.impl;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import backend.dao.AuthenticationDAO;
import backend.dao.GeneralDAO;
import backend.dto.LoggedUser;
import backend.dto.LoginDetails;
import backend.dto.NullLoggedUser;
import backend.exception.ApplicationUncheckedException;

public class AuthenticationDAOImpl extends GeneralDAO implements AuthenticationDAO {

	@Override
	public LoggedUser getUserByUsernameAndPassword(LoginDetails details) throws ApplicationUncheckedException {
		CriteriaBuilder criteriaBuilder = jpaRepository.criteriaBuilder();
		CriteriaQuery<LoggedUser> criteriaQuery = criteriaBuilder.createQuery(LoggedUser.class);
		Root<LoggedUser> from = criteriaQuery.from(LoggedUser.class);
		CriteriaQuery<LoggedUser> select = criteriaQuery.select(from);
		
		Predicate where = criteriaBuilder.disjunction();
		where = criteriaBuilder.and(where, criteriaBuilder.equal(from.get("userLogin"), details.getLogin()));
		where = criteriaBuilder.and(where, criteriaBuilder.equal(from.get("password"), details.getPassword()));
		
		Query query = jpaRepository.createQuery(select);
	    List<LoggedUser> resultList = query.getResultList();
	    
	    return resultList.isEmpty() ? new NullLoggedUser() : resultList.get(0); 
	}

}