package backend.exception;

import java.io.Serializable;

public class ApplicationUncheckedException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -5966684352181072896L;
    
    private String bundleKey;

    public ApplicationUncheckedException(String message) {
        super(message);
    }
    
    public ApplicationUncheckedException(String message, String bundleKey) {
        super(message);
        this.bundleKey = bundleKey;
    }

    public ApplicationUncheckedException(String string, Throwable throwable) {
        super(string, throwable);
    }
    
    public ApplicationUncheckedException(String string, String bundleKey, String[] params) {
        super(string);
        this.bundleKey = bundleKey;
    }

	public String getMessageKey() {
		return bundleKey;
	}
    
}
