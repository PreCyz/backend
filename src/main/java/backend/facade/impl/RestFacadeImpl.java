package backend.facade.impl;

import backend.dto.LoggedUser;
import backend.dto.LoginDetails;
import backend.facade.RestFacade;
import backend.service.impl.AuthenticationService;

public class RestFacadeImpl implements RestFacade {
	
	private AuthenticationService authenticationService;

	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@Override
	public LoggedUser login(LoginDetails loginDetails) {
		return authenticationService.getLoggedUser(loginDetails);
	}

	@Override
	public LoggedUser login(String login, String pass) {
		LoggedUser loggedUser = new LoggedUser();
		loggedUser.setUserLogin("gawa");
		return loggedUser;
	}

	@Override
	public String test() {
		return "Success";
	}

}
