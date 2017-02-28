package backend.dto;

public class NullLoggedUser extends LoggedUser {

	private static final long serialVersionUID = -7909486280745003289L;
	
	@Override
	public boolean isNullUser() {
		return true;
	}

}
