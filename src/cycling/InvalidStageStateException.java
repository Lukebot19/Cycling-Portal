package cycling;

/**
 * Thrown when attempting to perform an action within a stage that is
 * incompatible with its current state. For instance, when trying to add results
 * to a stage, but the stage is still under development, i.e., not concluded.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
public class InvalidStageStateException extends Exception {

	/**
	 * Constructs an instance of the exception with no message
	 */
	public InvalidStageStateException() {
		// do nothing
	}

	/**
	 * Constructs an instance of the exception containing the message argument
	 * 
	 * @param message message containing details regarding the exception cause
	 */
	public InvalidStageStateException(String message) {
		super(message);
	}

}
