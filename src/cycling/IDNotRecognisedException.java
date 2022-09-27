package cycling;

/**
 * Thrown when attempting to use an ID that does not exit in the system.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
public class IDNotRecognisedException extends Exception {

	/**
	 * Constructs an instance of the exception with no message
	 */
	public IDNotRecognisedException() {
		// do nothing
	}

	/**
	 * Constructs an instance of the exception containing the message argument
	 * 
	 * @param message message containing details regarding the exception cause
	 */
	public IDNotRecognisedException(String message) {
		super(message);
	}

}
