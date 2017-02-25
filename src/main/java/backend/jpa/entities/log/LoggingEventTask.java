package backend.jpa.entities.log;

public class LoggingEventTask extends LogSequenceEntry {
	
	private static final long serialVersionUID = -1609118027087562859L;
	
	private String message;
	private String ullaTaskId;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUllaTaskId() {
		return ullaTaskId;
	}
	public void setUllaTaskId(String ullaTaskId) {
		this.ullaTaskId = ullaTaskId;
	}
	public String getLevel() {
		return "DEBUG";
	}
	
}
