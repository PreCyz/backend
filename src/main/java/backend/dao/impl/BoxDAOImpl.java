package backend.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
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
import backend.exception.DAOException;
import backend.jpa.entity.BoxMessage;
import backend.jpa.entity.BoxMessageResponse;
import backend.util.BackendConstants;
import backend.util.TimeService;

public class BoxDAOImpl extends GeneralDAO implements BoxDAO {

	private static final Map<String, String> codes = new HashMap<String, String>();
	static {
		codes.put("offer", "657");
		codes.put("contract", "658");
		codes.put("premiums", "659");
		codes.put("dispositions", "660");
		codes.put("channels", "661");
		codes.put("complaint", "783");

		codes.put("payments_partialSurrender", "662");
		codes.put("payments_fullSurrender", "663");
		codes.put("payments_maturity", "664");
		codes.put("payments_death", "665");
	}
	
	@Override
	public String sendClientQuestion(String contractNumber, String type, String message) {
		return "";
	}

	@Override
	public Long saveMessage(BoxMessage message) {
		return saveFullyPreparedMessage(message);
	}

	private Long saveFullyPreparedMessage(BoxMessage message) {
		try {
		
			jpaRepository.save(message);
			
			return message.getId();
		} catch (Exception e) {
			String errMsg = "BoxDAOImpl.saveFullyPreparedMessage";
			DAOException daoException = new DAOException(errMsg, e);
			if (e.getMessage() != null && e.getMessage().startsWith("ORA-00001")) {
				daoException.setNotLog();
			} else {
				logger.error(errMsg, e);
				throw daoException;
			}
		}
		return null;
	}
	
	@Override
	public void saveProcessedMessage(BoxMessage message) {
		saveFullyPreparedMessage(message);
	}


	@Override
	public void updateMessageData(BoxMessage message) {
		try {			

			jpaRepository.save(message);
			
		} catch (Exception e) {
			String errMsg = "BoxDAOImpl.updateMessageData";
			logger.error(errMsg, e);
			throw new DAOException(errMsg, e);
		}
	}

	@Override
	public List<BoxMessage> getSavedMessages(String contractNumber) {

		try {
			Map<String, String> params = new LinkedHashMap<>();
			params.put("contractNumber", contractNumber);
			
			List<BoxMessage> messages = jpaRepository.findListWithNamedQuery(BoxMessage.class, "BoxMessage.findByContractNumberOrdered", params);
			
			return messages;
		} catch (Exception e) {
			String errMsg = "BoxDAOImpl.getSolMessages";
			logger.error(errMsg, e);
			throw new DAOException(errMsg, e);
		}
	}

	@SuppressWarnings("unchecked")
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
			throw new DAOException(errMsg, e);
		}
	}

	private boolean isIdsProper(String ids) {
		Pattern pattern = Pattern.compile("[0-9, ]*");
        Matcher matcher = pattern.matcher((String) ids);
        return matcher.matches();
	}

	@Override
	public List<BoxMessageResponse> getMessagesResponses(String contractNumber) {
		List<BoxMessageResponse> result = new LinkedList<>();
		try {
			List<BoxMessage> messages = new ArrayList<>();
			
			Map<String, String> params = new HashMap<>();
			params.put("contractNumber", contractNumber);
			
			messages.addAll(jpaRepository.findListWithNamedQuery(BoxMessage.class, "BoxMessage.findByContractNumber", params));
			
			messages.stream().forEach(boxMessage -> {
				if(boxMessage.getBoxMessageResponse() != null){
					result.add(boxMessage.getBoxMessageResponse());
				}
			});
		} catch (Exception e) {
			String errMsg = "BoxDAOImpl.getSolMessages";
			logger.error(errMsg, e);
			throw new DAOException(errMsg, e);
		}
		
		return result;
	}
	
	@Override
	public Set<String> getReadedMessages(String userName, String contractNumber) {
		Set<String> readed = new HashSet<String>();
		try {
			Map<String, String> params = new HashMap<>();
			params.put("userId", userName);
			readed.addAll(jpaRepository.findListWithNamedQuery(String.class, "BoxMessageStatus.findCaseIdByUserId", params));
		} catch (Exception e) {
			String errMsg = "BoxDAOImpl.getSolMessages";
			logger.error(errMsg, e);
			throw new DAOException(errMsg, e);
		}
		
		return readed;
	}

	@Override
	public Date getLastPartialSurrenderDate(String contractNumber) {
		String sql = "SELECT MAX(b.createDate) FROM BoxMessage b WHERE b.contractNumber= :contractNumber AND b.majorType= :majorType AND b.type= :type"; 
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
	   			     "and trunc(create_date) >= TO_DATE(?,'YYYY-MM-DD') and trunc(create_date) <= TO_DATE(?,'YYYY-MM-DD')";
		Query nativeQuery = jpaRepository.createNativeQuery(sql);
		nativeQuery.setParameter(1, contractNumber);
		nativeQuery.setParameter(2, "someType");
		nativeQuery.setParameter(3, BackendConstants.DEFAULT_HTML_DATE_FORMATTER.format(lastAnnualDate));
		nativeQuery.setParameter(4, BackendConstants.DEFAULT_HTML_DATE_FORMATTER.format(new Date(TimeService.currentTimeMillis())));
		return ((BigDecimal) nativeQuery.getSingleResult()).shortValue();
	}

}
