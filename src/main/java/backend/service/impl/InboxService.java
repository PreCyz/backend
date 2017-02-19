package backend.service.impl;

import java.util.List;

import backend.annotation.SecurityGuard;
import backend.dao.InboxDAO;
import backend.dto.BoxMessages;
import backend.dto.InboxDetails;
import backend.jpa.entities.BoxMessage;
import backend.service.AbstractService;

public class InboxService extends AbstractService {
	
	private InboxDAO inboxDAO;

	public InboxService(ExceptionThrowerService exceptionThrower) {
		super(exceptionThrower);
	}
	
	public void setInboxDAO(InboxDAO inboxDAO) {
		this.inboxDAO = inboxDAO;
	}

	@SecurityGuard(activeSessionRequired=true, accessToContractRequired=true)
	public BoxMessages getMessages(String contractNumber, InboxDetails inboxDetails) {
		exceptionThrower.throwNullOrEmpty(contractNumber, "contractNumber");
		exceptionThrower.throwNullOrEmpty(inboxDetails.getUserContractRole(), "userContractRole");
		return inboxDAO.getMessages(inboxDetails.getSecondaryUsername(), inboxDetails.getContractNumber(), inboxDetails.getUserContractRole());
	}

	@SecurityGuard
	public List<BoxMessage> getMessageDatails(String contractNumber, InboxDetails inboxDetails) {
		exceptionThrower.throwNullOrEmpty(inboxDetails.getBoxMessage(), "boxMessage");
		return inboxDAO.getMessageDatails(inboxDetails.getBoxMessage());
	}	
}
