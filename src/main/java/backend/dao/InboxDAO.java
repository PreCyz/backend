package backend.dao;

import java.util.List;

import backend.dto.BoxMessages;
import backend.jpa.entity.BoxMessage;

public interface InboxDAO {

	BoxMessages getMessages(String secondaryUsername, String contractNumber, String userContractRole);
	List<BoxMessage> getMessageDatails(BoxMessage message);
	//void setMessageReaded(LoggedUser user, String module, BoxMessage message);
	
	
}

