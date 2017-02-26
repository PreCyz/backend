package backend.aspect;

import backend.annotation.SecurityGuard;
import backend.dto.LoggedUser;
import backend.exception.AspectException;
import backend.helper.StringHelper;
import backend.initiator.LoggerInitiator;
import backend.service.SessionService;

public class ContractAccessAspect extends LoggerInitiator {

	private final static String ERROR_MSG_KEY = "backend.aspect.noAccessToContract";
	
	private final SessionService sessionService;

	public ContractAccessAspect(SessionService sessionService) {
		super();
		this.sessionService = sessionService;
	}
	
	public void checkContractAccess(SecurityGuard securityGuard, String contractNumber) {
		if ( !doCheck(securityGuard, contractNumber) ) {
			return;
		}
		LoggedUser user = sessionService.getUserWithActualSession();
		if ( !hasUserAccessToContract(contractNumber, user) ) {
			String errorMsg = String.format("User [%s] does not have access to contract [%s].", user.getUserLogin(), contractNumber);
			logger.error(errorMsg);
			throw new AspectException(errorMsg, ERROR_MSG_KEY, new String[]{user.getUserLogin(), contractNumber});
		}
		sessionService.addUserContractToSession(contractNumber);
	}
	
	private boolean doCheck(SecurityGuard securityGuard, String contractNumber) {
		return securityGuard.accessToContractRequired() && !StringHelper.empty(contractNumber);
	}
	
	private boolean hasUserAccessToContract(String contractNumber, LoggedUser user) {
		return true;
	}
	
	public void removeContractFromSession(SecurityGuard securityGuard, String contractNumber) {
		if ( securityGuard.accessToContractRequired() ) {
			sessionService.removeFromSession(sessionService.prepareKeyForContract());
		}
	}
}
