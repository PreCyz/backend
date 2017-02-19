package backend.dao.impl;

import backend.dao.AuthenticationDAO;
import backend.dao.GeneralDAO;
import backend.dto.LoggedUser;
import backend.dto.LoginDetails;
import backend.exception.DAOException;

public class AuthenticationDAOImpl extends GeneralDAO implements AuthenticationDAO {

	@Override
	public LoggedUser getUserByUsernameAndPassword(LoginDetails details) throws DAOException {
		LoggedUser loggedUser = new LoggedUser();
		loggedUser.setUserName("MyName");
		return loggedUser;
	}

	@Override
	public String webChangePassword(LoginDetails details) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unpairMobileDevice(String deviceId) {
		// TODO Auto-generated method stub
		
	}

}