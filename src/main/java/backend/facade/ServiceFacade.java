package backend.facade;

import backend.dto.LoggedUser;
import backend.dto.LoginDetails;

public interface ServiceFacade {
	
	LoggedUser login(LoginDetails loginDetails);

}
