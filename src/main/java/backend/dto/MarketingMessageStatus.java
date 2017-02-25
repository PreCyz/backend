package backend.dto;

import java.util.Date;

import backend.jpa.AutoIncrementEntry;

public class MarketingMessageStatus extends AutoIncrementEntry {

	private static final long serialVersionUID = 2066240271784234614L;
	
	private MarketingMessage message;
	private String messageId;
	private String userId;
	private Date readDate;
	private String module;
	
	private String onlyForContract;
	
	public MarketingMessage getMessage() {
		return message;
	}
	public void setMessage(MarketingMessage message) {
		this.message = message;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getReadDate() {
		return readDate;
	}
	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	
	public String getOnlyForContract() {
		return onlyForContract;
	}
	public void setOnlyForContract(String onlyForContract) {
		this.onlyForContract = onlyForContract;
	}

}
