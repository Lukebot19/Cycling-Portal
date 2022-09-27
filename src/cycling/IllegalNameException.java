package cycling;

/**
 * Thrown when attempting to assign a race name already in use in the
 * system.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
public class IllegalNameException extends Exception {

	/**
	 * Constructs an instance of the exception with no message
	 */
	public IllegalNameException() {
		// do nothing
	}

	/**
	 * Constructs an instance of the exception containing the message argument
	 * 
	 * @param message message containing details regarding the exception cause
	 */
	public IllegalNameException(String message) {
		super(message);
	}

}
