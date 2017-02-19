package backend.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_EMPTY)
public class LastLoginsInfo implements Serializable {

	private static final long serialVersionUID = -3488575804174951335L;
	
	private Date lastSuccessLogin;
	private Date lastFailureLogin;
	
	public LastLoginsInfo() {}
	
	public LastLoginsInfo(Date lastSuccessLogin, Date lastFailureLogin) {
		this.lastSuccessLogin = lastSuccessLogin;
		this.lastFailureLogin = lastFailureLogin;
	}

	public Date getLastSuccessLogin() {
		return lastSuccessLogin;
	}
	public void setLastSuccessLogin(Date lastSuccessLogin) {
		this.lastSuccessLogin = lastSuccessLogin;
	}
	public Date getLastFailureLogin() {
		return lastFailureLogin;
	}
	public void setLastFailureLogin(Date lastFailureLogin) {
		this.lastFailureLogin = lastFailureLogin;
	}
}
