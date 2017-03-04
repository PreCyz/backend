package backend.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import backend.dao.BoxDAO;
import backend.dao.GeneralDAO;
import backend.exception.ApplicationUncheckedException;

public class BoxDAOImpl extends GeneralDAO implements BoxDAO {
	
	@Override
	public String sendClientQuestion(String contractNumber, String type, String message) {
		return "";
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public List<BoxMessage> getSavedMessagesForIds(String ids) {
		if(!isIdsProper(ids)){
			return null;
		}
		
		try {			
			CriteriaBuilder criteriaBuilder = jpaRepository.criteriaBuilder();
			CriteriaQuery<BoxMessage> criteriaQuery = criteriaBuilder.createQuery(BoxMessage.class);
			Root<BoxMessage> boxMessage = criteriaQuery.from(BoxMessage.class);

			Predicate where = criteriaBuilder.disjunction();
			String[] idsArray = ids.split(",");
			for (String id : idsArray){
				Long longId = new Long(id);
				where = criteriaBuilder.or(where, criteriaBuilder.equal(boxMessage.get("id"), longId));
			}
			criteriaQuery.where(where);
			criteriaQuery.orderBy(criteriaBuilder.desc(boxMessage.get("id")));

			Query query = jpaRepository.createQuery(criteriaQuery);
			
			return query.getResultList();
		} catch (Exception e) {
			String errMsg = "BoxDAOImpl.getSolMessages";
			logger.error(errMsg, e);
			throw new ApplicationUncheckedException(errMsg, e);
		}
	}

	private boolean isIdsProper(String ids) {
		Pattern pattern = Pattern.compile("[0-9, ]*");
        Matcher matcher = pattern.matcher((String) ids);
        return matcher.matches();
	}*/
	
	@Override
	public Set<String> getReadedMessages(String userName, String contractNumber) {
		Set<String> readed = new HashSet<String>();
		try {
			Map<String, String> params = new HashMap<>();
			params.put("userId", userName);
			readed.addAll(
					jpaRepository.findListWithNamedQuery(String.class, "BoxMessageStatus.findCaseIdByUserId", params)
			);
		} catch (Exception e) {
			String errMsg = "BoxDAOImpl.getSolMessages";
			logger.error(errMsg, e);
			throw new ApplicationUncheckedException(errMsg, e);
		}
		
		return readed;
	}

	@Override
	public Date getLastPartialSurrenderDate(String contractNumber) {
		String sql = "SELECT MAX(b.createDate) FROM BoxMessage b WHERE b.contractNumber= :contractNumber " + 
				"AND b.majorType= :majorType AND b.type= :type"; 
		Query query = jpaRepository.createQuery(sql);
		query.setParameter("contractNumber", contractNumber);
		query.setParameter("majorType", "someType");
		query.setParameter("type", "PartialSurrender");
		return (Date) query.getSingleResult();
	}

	@Override
	public short getPartialSurrenderQuantityFromDate(String contractNumber, Date lastAnnualDate){
		String sql = "select count(mess_type) as resultBigDecimal from BOX_MESSAGE " +
	   			     "where contractno=? and major_type=? and mess_type='PartialSurrender' " + 
	   			     "and trunc(create_date) >= TO_DATE(?,'YYYY-MM-DD') " +
	   			     "and trunc(create_date) <= TO_DATE(?,'YYYY-MM-DD')";
		Query nativeQuery = jpaRepository.createNativeQuery(sql);
		nativeQuery.setParameter(1, contractNumber);
		nativeQuery.setParameter(2, "someType");
		nativeQuery.setParameter(3, new SimpleDateFormat("yyyy-mm-dd").format(lastAnnualDate));
		nativeQuery.setParameter(4, new SimpleDateFormat("yyyy-mm-dd").format(LocalDate.now()));
		return ((BigDecimal) nativeQuery.getSingleResult()).shortValue();
	}

}
