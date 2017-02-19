package backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_EMPTY)
public class LoggedUser {
	
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setSessionReferenceId(Long savedLogLoginId) {
		// TODO Auto-generated method stub
		
	}

	public String getAgentId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLicensNo() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAgentStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserLogin() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSessionId(String sessionId) {
		// TODO Auto-generated method stub
		
	}

	public String getSessionId() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasAuthority(String authority) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasContractAuthority(String contractNumber, String contractAuthority) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isMaintenanceUser() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getClientCode() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPassword(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setUserLogin(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setUserFirstName(String fIRST_NAME) {
		// TODO Auto-generated method stub
		
	}

	public void setUserSurname(String sURNAME) {
		// TODO Auto-generated method stub
		
	}

	public String[] extractNameAndSurname() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setUserFullName(String format) {
		// TODO Auto-generated method stub
		
	}
}
