package cycling;

/**
 * Thrown when attempting to add segments (sprints or mountains) to a time-trial stage.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
public class InvalidStageTypeException extends Exception {

	/**
	 * Constructs an instance of the exception with no message
	 */
	public InvalidStageTypeException() {
		// do nothing
	}

	/**
	 * Constructs an instance of the exception containing the message argument
	 * 
	 * @param message message containing details regarding the exception cause
	 */
	public InvalidStageTypeException(String message) {
		super(message);
	}

}
