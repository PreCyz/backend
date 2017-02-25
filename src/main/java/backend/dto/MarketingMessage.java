package backend.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import backend.jpa.AutoIncrementEntry;

public class MarketingMessage extends AutoIncrementEntry {

	private static final long serialVersionUID = -7871861150593326300L;
	
	//private String id;
	private String title;
	private String message;
	private Date startDate;
	private Date endDate;
	private String type;
	private String inactive;
	private String popup;
	private String eventsIds;
	private String fundIdSrc;
	private String fundIdDest;
	private Integer priority;
	
	private MarketingMessageConditions conditionsData;
	private String restrainToContracts = "false";
	private List<String> contracts;
	
	private boolean alreadyRead = false;
	private List<String> readedOnContracts;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInactive() {
		return inactive;
	}
	public void setInactive(String inactive) {
		this.inactive = inactive;
	}
	public boolean isMessageInactive() {
		return "true".equalsIgnoreCase(inactive);
	}
	public void setMessageInactive(boolean inactive) {
		this.inactive = inactive ? "true" : "false";
	}
	public boolean isMessagePopup() {
		return "D".equals(type) ? false : "true".equalsIgnoreCase(popup);
	}
	public void setMessagePopup(boolean popup) {
		this.popup = popup ? "true" : "false";
	}
	public void setPopup(String popup) {
		this.popup = popup;
	}
	public String getPopup() {
		return "D".equals(type) ? "false" : popup;
	}
	public String getEventsIds() {
		return eventsIds;
	}
	public void setEventsIds(String eventsIds) {
		this.eventsIds = eventsIds;
	}
	public String getFundIdSrc() {
		return fundIdSrc;
	}
	public void setFundIdSrc(String fundIdSrc) {
		this.fundIdSrc = fundIdSrc;
	}
	public String getFundIdDest() {
		return fundIdDest;
	}
	public void setFundIdDest(String fundIdDest) {
		this.fundIdDest = fundIdDest;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public int getIntPriority() {
		return priority == null ? 0 : priority;
	}
	
	@XmlTransient
	public String getConditions() {
		return MarketingMessageConditions.getMarshal(getConditionsData());
	}
	public void setConditions(String conditions) {
		this.conditionsData = MarketingMessageConditions.getUnmarshal(conditions);
	}
	
	public boolean isAlreadyRead() {
		return alreadyRead;
	}
	public void setAlreadyRead(boolean alreadyRead) {
		this.alreadyRead = alreadyRead;
	}
	public boolean isReadedOnContract(String contractno) {
		if (alreadyRead) {
			return true;
		}
		if (readedOnContracts == null) {
			return false;
		}
		return readedOnContracts.contains(contractno);
	}
	public synchronized void setReadedOnContract(String contractno) {
		if (readedOnContracts == null) {
			readedOnContracts = new ArrayList<String>();
		}
		readedOnContracts.add(contractno);
	}
	@XmlTransient
	public MarketingMessageConditions getConditionsData() {
		return conditionsData;
	}
	public void setConditionsData(MarketingMessageConditions conditionsData) {
		this.conditionsData = conditionsData;
	}
	public String getRestrainToContracts() {
		return restrainToContracts;
	}
	public void setRestrainToContracts(String restrainToContracts) {
		this.restrainToContracts = restrainToContracts;
	}
	
	@XmlTransient
	public List<String> getContracts() {
		return contracts;
	}
	public void setContracts(List<String> contracts) {
		this.contracts = contracts;
	}
	public String getContractsInfo() {
		if (contracts == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		int i = 50;
		for(String contract : contracts) {
			if (i == 0) {
				sb.append("...");
				break;
			}
			sb.append("\"").append(contract).append("\", ");
			--i;
		}
		
		return sb.toString();
	}
	
}
