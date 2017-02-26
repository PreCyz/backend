package backend.dto;


import backend.jpa.entity.BoxMessage;

public class InboxDetails {
	
	String secondaryUsername;
	String contractNumber;
	String userContractRole;
	String sessionId;
	BoxMessage boxMessage;
	String module;
	
	public String getSecondaryUsername() {
		return secondaryUsername;
	}
	public void setSecondaryUsername(String secondaryUsername) {
		this.secondaryUsername = secondaryUsername;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getUserContractRole() {
		return userContractRole;
	}
	public void setUserContractRole(String userContractRole) {
		this.userContractRole = userContractRole;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public BoxMessage getBoxMessage() {
		return boxMessage;
	}
	public void setBoxMessage(BoxMessage boxMessage) {
		this.boxMessage = boxMessage;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
}
