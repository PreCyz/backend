package backend.dao;

import backend.dto.LoggedUser;
import backend.dto.LoginDetails;
import backend.exception.ApplicationUncheckedException;

public interface AuthenticationDAO {
	
	LoggedUser getUserByUsernameAndPassword(LoginDetails details) throws ApplicationUncheckedException;
	String webChangePassword(LoginDetails details) throws ApplicationUncheckedException;
	void unpairMobileDevice(String deviceId);
	
}
