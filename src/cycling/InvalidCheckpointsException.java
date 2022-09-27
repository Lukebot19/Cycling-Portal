package cycling;

/**
 * Each race result should contains the times for each segment (mountain and
 * sprint) within a stage, plus the start time and the finish time. The list of checkpoints must
 * follow its chronological sequence, i.e., checkpoint_i {@literal <=} checkpoint_i+1.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
public class InvalidCheckpointsException extends Exception {

	/**
	 * Constructs an instance of the exception with no message
	 */
	public InvalidCheckpointsException() {
		// do nothing
	}

	/**
	 * Constructs an instance of the exception containing the message argument
	 * 
	 * @param message message containing details regarding the exception cause
	 */
	public InvalidCheckpointsException(String message) {
		super(message);
	}

}
