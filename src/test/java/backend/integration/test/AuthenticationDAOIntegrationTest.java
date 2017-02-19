package backend.integration.test;

import backend.dao.AuthenticationDAO;
import backend.dto.LoggedUser;
import backend.dto.LoginDetails;
import backend.dto.LoginMode;
import backend.helper.StringHelper;


public class AuthenticationDAOIntegrationTest extends AbstractIntegrationTest {
	
	private AuthenticationDAO dao;
	public AuthenticationDAOIntegrationTest(AuthenticationDAO dao) {
		this.dao = dao;
	}
	
	@Override
	protected void executeTests() throws Exception {
		whenUserLoginClientThenProperResult();
	}
	
	private void whenUserLoginClientThenProperResult() throws Exception {
		displayTestName();
		
		LoginDetails details = createLoginDetails();
		
		LoggedUser user = dao.getUserByUsernameAndPassword(details);
		
		boolean condition = user != null;
		String msg = String.format("Niepusty obiekt LoggedUser (użytkownik się zalogował) [%s]", condition);
		assertion(msg, condition);
		
		condition = details.getLogin().equalsIgnoreCase(user.getUserLogin());
		msg = String.format("Zalogował się użytkownik o loginie [%s] [%s]", user.getUserLogin(), condition);
		assertion(msg, condition);
		
		condition = StringHelper.empty(user.getAgentId());
		msg = String.format("Użytkownik jest klientem [%s]", condition);
		assertion(msg, condition);
	}

	private LoginDetails createLoginDetails() {
		LoginDetails details = new LoginDetails();
		details.setLogin("cmax");
		details.setPassword("Skandia1");
		details.setMode(LoginMode.SOL);
		return details;
	}
}
