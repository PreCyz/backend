package backend.dao;

import java.util.Date;
import java.util.Set;

public interface BoxDAO {

	String sendClientQuestion(String contractNumber, String type, String message);
	
	Set<String> getReadedMessages(String userName, String contractNumber);
	
	Date getLastPartialSurrenderDate(String contractNumber);
	short getPartialSurrenderQuantityFromDate(String contractNumber, Date lastAnnualDate);
	
}
