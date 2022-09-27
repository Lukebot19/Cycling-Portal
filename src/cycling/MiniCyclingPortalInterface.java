package cycling;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * MiniCyclingPortalInterface interface. The no-argument constructor of a class
 * implementing this interface should initialise the MiniCyclingPortalInterface as
 * an empty platform with no initial racing teams nor races within it.
 * 
 * @author Diogo Pacheco
 * @version 1.2
 *
 */
public interface MiniCyclingPortalInterface extends Serializable {

	/**
	 * Get the races currently created in the platform.
	 * 
	 * @return An array of race IDs in the system or an empty array if none exists.
	 */
	int[] getRaceIds();

	/**
	 * The method creates a staged race in the platform with the given name and
	 * description.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param name        Race's name.
	 * @param description Race's description (can be null).
	 * @throws IllegalNameException If the name already exists in the platform.
	 * @throws InvalidNameException If the name is null, empty, has more than 30
	 *                              characters, or has white spaces.
	 * @return the unique ID of the created race.
	 * 
	 */
	int createRace(String name, String description) throws IllegalNameException, InvalidNameException;

	/**
	 * Get the details from a race.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return Any formatted string containing the race ID, name, description, the
	 *         number of stages, and the total length (i.e., the sum of all stages'
	 *         length).
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
	String viewRaceDetails(int raceId) throws IDNotRecognisedException;

	/**
	 * The method removes the race and all its related information, i.e., stages,
	 * segments, and results.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race to be removed.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
	void removeRaceById(int raceId) throws IDNotRecognisedException;

	/**
	 * The method queries the number of stages created for a race.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return The number of stages created for the race.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
	int getNumberOfStages(int raceId) throws IDNotRecognisedException;

	/**
	 * Creates a new stage and adds it to the race.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId      The race which the stage will be added to.
	 * @param stageName   An identifier name for the stage.
	 * @param description A descriptive text for the stage.
	 * @param length      Stage length in kilometres.
	 * @param startTime   The date and time in which the stage will be raced. It
	 *                    cannot be null.
	 * @param type        The type of the stage. This is used to determine the
	 *                    amount of points given to the winner.
	 * @return the unique ID of the stage.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 * @throws IllegalNameException     If the name already exists in the platform.
	 * @throws InvalidNameException     If the name is null, empty, has more than 30
	 *                              	characters, or has white spaces.
	 * @throws InvalidLengthException   If the length is less than 5km.
	 */
	int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException;

	/**
	 * Retrieves the list of stage IDs of a race.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return An array of stage IDs ordered (from first to last) by their sequence in the
	 *         race or an empty array if none exists.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
	int[] getRaceStages(int raceId) throws IDNotRecognisedException;

	/**
	 * Gets the length of a stage in a race, in kilometres.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The stage's length.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
	double getStageLength(int stageId) throws IDNotRecognisedException;

	/**
	 * Removes a stage and all its related data, i.e., segments and results.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being removed.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
	void removeStageById(int stageId) throws IDNotRecognisedException;

	/**
	 * Adds a climb segment to a stage.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId         The ID of the stage to which the climb segment is
	 *                        being added.
	 * @param location        The kilometre location where the climb finishes within
	 *                        the stage.
	 * @param type            The category of the climb - {@link SegmentType#C4},
	 *                        {@link SegmentType#C3}, {@link SegmentType#C2},
	 *                        {@link SegmentType#C1}, or {@link SegmentType#HC}.
	 * @param averageGradient The average gradient for the climb.
	 * @param length          The length of the climb in kilometre.
	 * @return The ID of the segment created.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidLocationException   If the location is out of bounds of the
	 *                                    stage length.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 * @throws InvalidStageTypeException  Time-trial stages cannot contain any
	 *                                    segment.
	 */
	int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException;

	/**
	 * Adds an intermediate sprint to a stage.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId  The ID of the stage to which the intermediate sprint segment
	 *                 is being added.
	 * @param location The kilometre location where the intermediate sprint finishes
	 *                 within the stage.
	 * @return The ID of the segment created.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidLocationException   If the location is out of bounds of the
	 *                                    stage length.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 * @throws InvalidStageTypeException  Time-trial stages cannot contain any
	 *                                    segment.
	 */
	int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException;

	/**
	 * Removes a segment from a stage.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param segmentId The ID of the segment to be removed.
	 * @throws IDNotRecognisedException   If the ID does not match to any segment in
	 *                                    the system.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 */
	void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException;

	/**
	 * Concludes the preparation of a stage. After conclusion, the stage's state
	 * should be "waiting for results".
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage to be concluded.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 */
	void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException;

	/**
	 * Retrieves the list of segment (mountains and sprints) IDs of a stage.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The list of segment IDs ordered (from first to last) by their location in the
	 *         stage.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
	int[] getStageSegments(int stageId) throws IDNotRecognisedException;

	/**
	 * Creates a team with name and description.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param name        The identifier name of the team.
	 * @param description A description of the team.
	 * @return The ID of the created team.
	 * @throws IllegalNameException If the name already exists in the platform.
	 * @throws InvalidNameException If the name is null, empty, has more than 30
	 *                              characters, or has white spaces.
	 */
	int createTeam(String name, String description) throws IllegalNameException, InvalidNameException;

	/**
	 * Removes a team from the system.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param teamId The ID of the team to be removed.
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
	 */
	void removeTeam(int teamId) throws IDNotRecognisedException;

	/**
	 * Get the list of teams' IDs in the system.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @return The list of IDs from the teams in the system. An empty list if there
	 *         are no teams in the system.
	 * 
	 */
	int[] getTeams();

	/**
	 * Get the riders of a team.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param teamId The ID of the team being queried.
	 * @return A list with riders' ID.
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
	 */
	int[] getTeamRiders(int teamId) throws IDNotRecognisedException;

	/**
	 * Creates a rider.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param teamID      The ID rider's team.
	 * @param name        The name of the rider.
	 * @param yearOfBirth The year of birth of the rider.
	 * @return The ID of the rider in the system.
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
	 * @throws IllegalArgumentException If the name of the rider is null or the year
	 *                                  of birth is less than 1900.
	 */
	int createRider(int teamID, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException;

	/**
	 * Removes a rider from the system. When a rider is removed from the platform,
	 * all of its results should be also removed. Race results must be updated.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param riderId The ID of the rider to be removed.
	 * @throws IDNotRecognisedException If the ID does not match to any rider in the
	 *                                  system.
	 */
	void removeRider(int riderId) throws IDNotRecognisedException;

	/**
	 * Record the times of a rider in a stage.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId     The ID of the stage the result refers to.
	 * @param riderId     The ID of the rider.
	 * @param checkpoints An array of times at which the rider reached each of the
	 *                    segments of the stage, including the start time and the
	 *                    finish line.
	 * @throws IDNotRecognisedException    If the ID does not match to any rider or
	 *                                     stage in the system.
	 * @throws DuplicatedResultException   Thrown if the rider has already a result
	 *                                     for the stage. Each rider can have only
	 *                                     one result per stage.
	 * @throws InvalidCheckpointsException Thrown if the length of checkpoints is
	 *                                     not equal to n+2, where n is the number
	 *                                     of segments in the stage; +2 represents
	 *                                     the start time and the finish time of the
	 *                                     stage.
	 * @throws InvalidStageStateException  Thrown if the stage is not "waiting for
	 *                                     results". Results can only be added to a
	 *                                     stage while it is "waiting for results".
	 */
	void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints) throws IDNotRecognisedException,
			DuplicatedResultException, InvalidCheckpointsException, InvalidStageStateException;

	/**
	 * Get the times of a rider in a stage.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
	 * @return The array of times at which the rider reached each of the segments of
	 *         the stage and the total elapsed time. The elapsed time is the
	 *         difference between the finish time and the start time. Return an
	 *         empty array if there is no result registered for the rider in the
	 *         stage.
	 * @throws IDNotRecognisedException If the ID does not match to any rider or
	 *                                  stage in the system.
	 */
	LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException;

	/**
	 * For the general classification, the aggregated time is based on the adjusted
	 * elapsed time, not the real elapsed time. Adjustments are made to take into
	 * account groups of riders finishing very close together, e.g., the peloton. If
	 * a rider has a finishing time less than one second slower than the
	 * previous rider, then their adjusted elapsed time is the smallest of both. For
	 * instance, a stage with 200 riders finishing "together" (i.e., less than 1
	 * second between consecutive riders), the adjusted elapsed time of all riders
	 * should be the same as the first of all these riders, even if the real gap
	 * between the 200th and the 1st rider is much bigger than 1 second. There is no
	 * adjustments on elapsed time on time-trials.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
	 * @return The adjusted elapsed time for the rider in the stage. Return null if 
	 * 		  there is no result registered for the rider in the stage.
	 * @throws IDNotRecognisedException   If the ID does not match to any rider or
	 *                                    stage in the system.
	 */
	LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId)
			throws IDNotRecognisedException;

	/**
	 * Removes the stage results from the rider.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
	 * @throws IDNotRecognisedException If the ID does not match to any rider or
	 *                                  stage in the system.
	 */
	void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException;

	/**
	 * Get the riders finished position in a a stage.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return A list of riders ID sorted by their elapsed time. An empty list if
	 *         there is no result for the stage.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
	int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException;

	/**
	 * Get the adjusted elapsed times of riders in a stage.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of adjusted elapsed times sorted by their finish
	 *         time. An empty list if there is no result for the stage. These times
	 *         should match the riders returned by
	 *         {@link #getRidersRankInStage(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
	LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException;

	/**
	 * Get the number of points obtained by each rider in a stage.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of points each riders received in the stage, sorted
	 *         by their elapsed time. An empty list if there is no result for the
	 *         stage. These points should match the riders returned by
	 *         {@link #getRidersRankInStage(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
	int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException;

	/**
	 * Get the number of mountain points obtained by each rider in a stage.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of mountain points each riders received in the stage,
	 *         sorted by their finish time. An empty list if there is no result for
	 *         the stage. These points should match the riders returned by
	 *         {@link #getRidersRankInStage(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
	int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException;

	/**
	 * Method empties this MiniCyclingPortalInterface of its contents and resets all
	 * internal counters.
	 */
	void eraseCyclingPortal();

	/**
	 * Method saves this MiniCyclingPortalInterface contents into a serialised file,
	 * with the filename given in the argument.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 *
	 * @param filename Location of the file to be saved.
	 * @throws IOException If there is a problem experienced when trying to save the
	 *                     store contents to the file.
	 */
	void saveCyclingPortal(String filename) throws IOException;

	/**
	 * Method should load and replace this MiniCyclingPortalInterface contents with the
	 * serialised contents stored in the file given in the argument.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 *
	 * @param filename Location of the file to be loaded.
	 * @throws IOException            If there is a problem experienced when trying
	 *                                to load the store contents from the file.
	 * @throws ClassNotFoundException If required class files cannot be found when
	 *                                loading.
	 */
	void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException;

}
