/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package  com.kushnerov.paymentSystem.exceptions;


public class PaySysException extends Exception {
   
	private static final long serialVersionUID = 1343496147896122607L;

	
    public PaySysException() {
        super();
    }

   
    public PaySysException(String message) {
        super(message);
    }

  
    public PaySysException(String message, Exception cause) {
        super(message, cause);
    }

    
    public PaySysException(Exception cause) {
        super(cause);
    }

    
    protected PaySysException(String message, Exception cause,
                          boolean enableSuppression,
                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
