package backend.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
@JsonInclude(value=Include.NON_EMPTY)
public class LoginDetails {
	private String login;
	private String password;
	private String deviceId; 
	private String securityCode;
	private String deviceName;
	
	private String newPassword;
	
	public LoginDetails() {}

	public LoginDetails(String login, String password, String deviceId, String securityCode, String deviceName) {
		this.login = login;
		this.password = password;
		this.deviceId = deviceId;
		this.securityCode = securityCode;
		this.deviceName = deviceName;
	}

	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
