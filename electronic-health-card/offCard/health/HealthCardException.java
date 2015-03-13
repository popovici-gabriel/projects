/**
 * 
 */
package health;

/**
 * @author Gaby Popovici
 *
 */
	/**
	 * This Exception is thrown by methods of <tt>HealthCard</tt> when a problem occurrs.
	 *  
	 */
	public class HealthCardException extends Exception {
	/*
	 * Creates a new <tt>HealthCardException</tt> object with the given error message.
	 */
	public HealthCardException(String message) {
		super(message);
	}
}
