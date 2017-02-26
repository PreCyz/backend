package backend.exception;

import java.io.Serializable;

public class ApplicationCheckedException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -5966684352181072896L;

    public ApplicationCheckedException(String string) {
        super(string);
    }

    public ApplicationCheckedException(String string, Throwable throwable) {
        super(string, throwable);
    }
    
}
