package backend.service.impl;

import backend.dto.LoggedUser;
import backend.service.SessionService;

public class SecurityService {
	
	private final SessionService sessionService;

	public SecurityService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	public final boolean hasAllContractAuthorities(String contractNumber, String[] authorities) {
		if (authorities == null) {
			return true;
		}
		for(String authority : authorities) {
			LoggedUser user = sessionService.getUserWithActualSession();
			if (!user.hasContractAuthority(contractNumber, authority)) {
				return false;
			}
		}
		return true;
	}
	
}
