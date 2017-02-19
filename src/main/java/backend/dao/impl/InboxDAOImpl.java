package backend.dao.impl;

import java.util.List;

import backend.dao.AuthenticationDAO;
import backend.dao.BoxDAO;
import backend.dao.GeneralDAO;
import backend.dao.InboxDAO;
import backend.dto.BoxMessages;
import backend.jpa.entities.BoxMessage;

public class InboxDAOImpl extends GeneralDAO implements InboxDAO {
	
    private BoxDAO boxDao;
    
	private AuthenticationDAO authenticationDao;
	
	public void setBoxDao(BoxDAO boxDao) {
		this.boxDao = boxDao;
	}

	public void setAuthenticationDao(AuthenticationDAO authenticationDao) {
		this.authenticationDao = authenticationDao;
	}

	@Override
	public BoxMessages getMessages(String secondaryUsername, String contractNumber, String userContractRole) {
		return new BoxMessages();
	}

	@Override
	public List<BoxMessage> getMessageDatails(BoxMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

}
