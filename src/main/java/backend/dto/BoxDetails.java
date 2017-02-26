package backend.dto;

import java.util.Date;

import backend.jpa.entity.BoxMessage;

public class BoxDetails {
	
	String contractNumber;
	String type;
	String message;
	String ids;
	String username;
	
	BoxMessage data;
	BoxMessage boxMessage;
	
	Date processingDate;
	Date lastAnnualDate;
	
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public BoxMessage getData() {
		return data;
	}
	public void setData(BoxMessage data) {
		this.data = data;
	}
	public BoxMessage getBoxMessage() {
		return boxMessage;
	}
	public void setBoxMessage(BoxMessage boxMessage) {
		this.boxMessage = boxMessage;
	}
	public Date getProcessingDate() {
		return processingDate;
	}
	public void setProcessingDate(Date processingDate) {
		this.processingDate = processingDate;
	}
	public Date getLastAnnualDate() {
		return lastAnnualDate;
	}
	public void setLastAnnualDate(Date lastAnnualDate) {
		this.lastAnnualDate = lastAnnualDate;
	}

}