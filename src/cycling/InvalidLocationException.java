package cycling;

/**
 * Thrown when attempting to assign a location outside the bounds of the stage,
 * i.e. 0 {@literal < } location {@literal <=} stage's length.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
public class InvalidLocationException extends Exception {

	/**
	 * Constructs an instance of the exception with no message
	 */
	public InvalidLocationException() {
		// do nothing
	}

	/**
	 * Constructs an instance of the exception containing the message argument
	 * 
	 * @param message message containing details regarding the exception cause
	 */
	public InvalidLocationException(String message) {
		super(message);
	}

}

