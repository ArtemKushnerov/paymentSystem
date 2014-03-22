package  com.kushnerov.paymentSystem.exceptions;


public class MyConnectionException extends PaySysException {
    
	private static final long serialVersionUID = -6348714694053292022L;

	
    public MyConnectionException() {
        super();
    }

    
    public MyConnectionException(String message) {
        super(message);
    }

   
    public MyConnectionException(String message, Exception cause) {
        super(message, cause);
    }

   
    public MyConnectionException(Exception cause) {
        super(cause);
    }

    
    protected MyConnectionException(String message, Exception cause,
                                    boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
