package backend.interceptor;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ErrorDetails {
	
	private final int code;
	private final String description;

	@JsonCreator
	public ErrorDetails(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
