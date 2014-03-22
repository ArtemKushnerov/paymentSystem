package  com.kushnerov.paymentSystem.exceptions;


public class MyCommandException extends PaySysException {
  
    
	private static final long serialVersionUID = -484662052331423862L;


	public MyCommandException() {
        super();
    }

   
    public MyCommandException(String message) {
        super(message);
    }

   
    public MyCommandException(String message, Exception cause) {
        super(message, cause);
    }

   
    public MyCommandException(Exception cause) {
        super(cause);
    }

   
    protected MyCommandException(String message, Exception cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
