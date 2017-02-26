package backend.exception;

public class MonitorException extends ApplicationCheckedException {

	private static final long serialVersionUID = 5497422801548094197L;
	
	private String message;
	private Throwable exception;
	
	public MonitorException(Throwable exception) {
		super(exception.getMessage());
		this.exception = exception;
	}
	
	public MonitorException(String message, Throwable exception) {
		super(message, exception);
		this.message = message;
		this.exception = exception;
	}

	public String getMessage() {
		return message;
	}

	public Throwable getException() {
		return exception;
	}

}
