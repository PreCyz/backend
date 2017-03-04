package backend.facade;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import backend.dto.LoggedUser;
import backend.dto.LoginDetails;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL) //optional
public interface WebServiceFacade extends ServiceFacade {
	
	@WebMethod LoggedUser login(@WebParam(name = "loginDetails") LoginDetails loginDetails);

}
