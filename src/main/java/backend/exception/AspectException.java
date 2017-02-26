package backend.exception;

import java.io.Serializable;

public class AspectException extends ApplicationUncheckedException implements Serializable {

	private static final long serialVersionUID = -7895026171777292082L;

	public AspectException(String string) {
        super(string);
    }

    public AspectException(String string, Throwable throwable) {
        super(string, throwable);
    }
    
    public AspectException(String string, String bundleKey, String[] params) {
        super(string);
    }
    
}
