package backend.facade.impl;

import javax.jws.WebService;

import backend.dto.LoggedUser;
import backend.dto.LoginDetails;
import backend.facade.WebServiceFacade;
import backend.service.impl.AuthenticationService;

@WebService(
		endpointInterface = "backend.facade.WebServiceFacade",
		serviceName = "MyExampleServiceName"
)
public class WebServiceFacadeImpl implements WebServiceFacade {
	
	private AuthenticationService authenticationService;
	
	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@Override
	public LoggedUser login(LoginDetails loginDetails) {
		return authenticationService.getLoggedUser(loginDetails);
	}

}
