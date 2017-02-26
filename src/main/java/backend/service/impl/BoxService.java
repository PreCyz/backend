package backend.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import backend.annotation.SecurityGuard;
import backend.dao.BoxDAO;
import backend.dto.BoxDetails;
import backend.jpa.entity.BoxMessage;
import backend.jpa.entity.BoxMessageResponse;
import backend.service.AbstractService;
import backend.service.SessionService;

@Service
public class BoxService extends AbstractService {
	
	public BoxService(ExceptionThrowerService exceptionThrower) {
		super(exceptionThrower);
	}

	private BoxDAO boxDAO;
	
	private SessionService sessionService;
	
	public void setBoxDAO(BoxDAO boxDAO) {
		this.boxDAO = boxDAO;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@SecurityGuard
	public String sendClientQuestion(String contractNumber, BoxDetails boxDetails) {
		exceptionThrower.throwNullOrEmpty(boxDetails.getContractNumber(), "contractNumber");
		exceptionThrower.throwNullOrEmpty(boxDetails.getType(), "type");
		exceptionThrower.throwNullOrEmpty(boxDetails.getMessage(), "message");
		return boxDAO.sendClientQuestion(boxDetails.getContractNumber(), boxDetails.getType(), boxDetails.getMessage());
	}

	@SecurityGuard
	public Long saveMessage(String contractNumber, BoxDetails boxDetails){
		exceptionThrower.throwNullOrEmpty(contractNumber, "contractNumber");
		return boxDAO.saveMessage(boxDetails.getBoxMessage());
	}

	@SecurityGuard
	public List<BoxMessage> getSavedMessages(String contractNumber, BoxDetails boxDetails){
		exceptionThrower.throwNullOrEmpty(boxDetails.getContractNumber(), "contractNumber");
		return boxDAO.getSavedMessages(boxDetails.getContractNumber());
	}
	
	@SecurityGuard
	public List<BoxMessage> getSavedMessagesForIds(BoxDetails boxDetails){
		exceptionThrower.throwNullOrEmpty(boxDetails.getIds(), "ids");
		return boxDAO.getSavedMessagesForIds(boxDetails.getIds());
	}
	
	@SecurityGuard
	public List<BoxMessageResponse> getMessagesResponses(String contractNumber, BoxDetails boxDetails){
		exceptionThrower.throwNullOrEmpty(boxDetails.getContractNumber(), "contractNumber");
		return boxDAO.getMessagesResponses(boxDetails.getContractNumber());
	}
	
//	public BoxMessage getSavedMessageDatails(BoxDetails boxDetails){
//		throwExceptionIfBoxMessageParamEmpty(boxDetails.getBoxMessage());
//		LoggedUser user  = sessionService.getUserFromSession();
//		return boxDAO.getSavedMessageDatails(user, boxDetails.getBoxMessage());
//	}
	
	@SecurityGuard
	public Set<String> getReadedMessages(String contractNumber, BoxDetails boxDetails){
		exceptionThrower.throwNullOrEmpty(boxDetails.getContractNumber(), "contractNumber");
		return boxDAO.getReadedMessages(sessionService.getUserWithActualSession().getUserName(), 
				boxDetails.getContractNumber());
	}
	
	@SecurityGuard
	public Date getLastPartialSurrenderDate(String contractNumber, BoxDetails boxDetails){
		exceptionThrower.throwNullOrEmpty(boxDetails.getContractNumber(), "contractNumber");
		return boxDAO.getLastPartialSurrenderDate(boxDetails.getContractNumber());
	}
	
	@SecurityGuard
	public short getPartialSurrenderQuantityFromDate(String contractNumber, BoxDetails boxDetails){
		exceptionThrower.throwNullOrEmpty(boxDetails.getContractNumber(), "contractNumber");
		exceptionThrower.throwNullOrEmpty(boxDetails.getLastAnnualDate(), "lastAnnualDate");
		return boxDAO.getPartialSurrenderQuantityFromDate(boxDetails.getContractNumber(), boxDetails.getLastAnnualDate());
	}
	
	public void saveProcessedMessage(BoxDetails boxDetails){
		exceptionThrower.throwNullOrEmpty(boxDetails.getBoxMessage(), "boxMessage");
		boxDAO.saveProcessedMessage(boxDetails.getBoxMessage());
	}
	
	public void updateMessageData(BoxDetails boxDetails){
		exceptionThrower.throwNullOrEmpty(boxDetails.getBoxMessage(), "boxMessage");
		boxDAO.updateMessageData(boxDetails.getBoxMessage());
	}
}
