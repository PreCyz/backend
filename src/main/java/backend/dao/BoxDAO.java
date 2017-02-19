package backend.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import backend.jpa.entities.BoxMessage;
import backend.jpa.entities.BoxMessageResponse;

public interface BoxDAO {

	String sendClientQuestion(String contractNumber, String type, String message);
	Long saveMessage(BoxMessage data);
	public void saveProcessedMessage(BoxMessage message);
	void updateMessageData(BoxMessage message);
	
	List<BoxMessage> getSavedMessages(String contractNumber);
	List<BoxMessage> getSavedMessagesForIds(String ids);
	List<BoxMessageResponse> getMessagesResponses(String contractNumber);
	//ta funkcja nic nie robi
	//BoxMessage getSavedMessageDatails(LoggedUser user, BoxMessage message);
	
	Set<String> getReadedMessages(String userName, String contractNumber);
	
	Date getLastPartialSurrenderDate(String contractNumber);
	short getPartialSurrenderQuantityFromDate(String contractNumber, Date lastAnnualDate);
	
	static final String QUESTION_PDF_INDEX = "F-PYT-1303";
}
