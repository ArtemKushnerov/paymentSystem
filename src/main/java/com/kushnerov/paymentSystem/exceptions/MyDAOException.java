package  com.kushnerov.paymentSystem.exceptions;


public class MyDAOException extends PaySysException {
   
	private static final long serialVersionUID = 6789819878236032110L;

	
    public MyDAOException() {
        super();
    }

    
    public MyDAOException(String message) {
        super(message);
    }

    
    public MyDAOException(String message, Exception cause) {
        super(message, cause);
    }

    
    public MyDAOException(Exception cause) {
        super(cause);
    }

    
    protected MyDAOException(String message, Exception cause,
                             boolean enableSuppression,
                             boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
