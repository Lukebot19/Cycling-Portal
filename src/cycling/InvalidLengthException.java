package cycling;

/**
 * Thrown when attempting to assign a race length null or less than 5 (kilometres).
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
public class InvalidLengthException extends Exception {

	/**
	 * Constructs an instance of the exception with no message
	 */
	public InvalidLengthException() {
		// do nothing
	}

	/**
	 * Constructs an instance of the exception containing the message argument
	 * 
	 * @param message message containing details regarding the exception cause
	 */
	public InvalidLengthException(String message) {
		super(message);
	}

}
