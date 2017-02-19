package backend.dao;

import backend.dto.LoggedUser;
import backend.dto.LoginDetails;
import backend.exception.DAOException;

public interface AuthenticationDAO {
	
	LoggedUser getUserByUsernameAndPassword(LoginDetails details) throws DAOException;
	String webChangePassword(LoginDetails details) throws DAOException;
	void unpairMobileDevice(String deviceId);
	
}
