package cycling;

import java.time.LocalTime;

/**
 * CyclingPortalInterface interface. The no-argument constructor of a class
 * implementing this interface should initialise the CyclingPortalInterface as
 * an empty platform with no initial racing teams nor races within it. For pair
 * submissions ONLY.
 * 
 * @author Diogo Pacheco
 * @version 1.1
 *
 */
public interface CyclingPortalInterface extends MiniCyclingPortalInterface {

	/**
	 * The method removes the race and all its related information, i.e., stages,
	 * segments, and results.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param name The name of the race to be removed.
	 * @throws NameNotRecognisedException If the name does not match to any race in
	 *                                    the system.
	 */
	void removeRaceByName(String name) throws NameNotRecognisedException;

	/**
	 * Get the general classification rank of riders in a race.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged 'if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return A ranked list of riders' IDs sorted ascending by the sum of their
	 *         adjusted elapsed times in all stages of the race. That is, the first
	 *         in this list is the winner (least time). An empty list if there is no
	 *         result for any stage in the race.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
	int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException;

	/**
	 * Get the general classification times of riders in a race.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return A list of riders' times sorted by the sum of their adjusted elapsed
	 *         times in all stages of the race. An empty list if there is no result
	 *         for any stage in the race. These times should match the riders
	 *         returned by {@link #getRidersGeneralClassificationRank(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
	LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException;

	/**
	 * Get the overall points of riders in a race.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return A list of riders' points (i.e., the sum of their points in all stages
	 *         of the race), sorted by the total elapsed time. An empty list if
	 *         there is no result for any stage in the race. These points should
	 *         match the riders returned by {@link #getRidersGeneralClassificationRank(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
	int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException;

	/**
	 * Get the overall mountain points of riders in a race.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return A list of riders' mountain points (i.e., the sum of their mountain
	 *         points in all stages of the race), sorted by the total elapsed time.
	 *         An empty list if there is no result for any stage in the race. These
	 *         points should match the riders returned by
	 *         {@link #getRidersGeneralClassificationRank(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
	int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException;

	/**
	 * Get the ranked list of riders based on the points classification in a race.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return A ranked list of riders' IDs sorted descending by the sum of their
	 *         points in all stages of the race. That is, the first in this list is
	 *         the winner (more points). An empty list if there is no result for any
	 *         stage in the race.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
	int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException;

	/**
	 * Get the ranked list of riders based on the mountain classification in a race.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return A ranked list of riders' IDs sorted descending by the sum of their
	 *         mountain points in all stages of the race. That is, the first in this
	 *         list is the winner (more points). An empty list if there is no result
	 *         for any stage in the race.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
	int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException;

}
