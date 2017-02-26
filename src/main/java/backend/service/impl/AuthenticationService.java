package backend.service.impl;

import backend.annotation.SecurityGuard;
import backend.dao.AuthenticationDAO;
import backend.dto.LoggedUser;
import backend.dto.LoginDetails;
import backend.service.AbstractService;
import backend.service.SessionService;

public class AuthenticationService extends AbstractService {
	
	private AuthenticationDAO authenticationDao;
	private SessionService sessionService;

	public AuthenticationService(ExceptionThrowerService exceptionThrower) {
		super(exceptionThrower);
	}
	
	public void setAuthenticationDao(AuthenticationDAO authenticationDao) {
		this.authenticationDao = authenticationDao;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	public LoggedUser getLoggedUser(LoginDetails details) {
		throwExceptionIfBasicInfoMissing(details);
		
		LoggedUser user = authenticationDao.getUserByUsernameAndPassword(details);
		user.setSessionId(sessionService.getSessionId());
		
		sessionService.addToSession(user.getSessionId(), user);
		
		return user;
	}

	private void throwExceptionIfBasicInfoMissing(LoginDetails details) {
		exceptionThrower.throwNullOrEmpty(details, "loginDetails");
		exceptionThrower.throwNullOrEmpty(details.getLogin(), "login");
		exceptionThrower.throwNullOrEmpty(details.getPassword(), "currentPassword");
	}
	
	private void throwExceptionIfEmptyNewPassword(LoginDetails details) {
		exceptionThrower.throwNullOrEmpty(details.getNewPassword(), "newPassword");
	}

	@SecurityGuard(activeSessionRequired=true)
	public void unpairMobileDevice(String deviceId) {
		exceptionThrower.throwNullOrEmpty(deviceId, "deviceId");
		authenticationDao.unpairMobileDevice(deviceId);
	}

	@SecurityGuard(activeSessionRequired=true)
	public String webChangePassword(LoginDetails details) {
		throwExceptionIfBasicInfoMissing(details);
		throwExceptionIfEmptyNewPassword(details);
		return authenticationDao.webChangePassword(details);
	}

}
