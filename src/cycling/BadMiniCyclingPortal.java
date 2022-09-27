package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * BadMiniCyclingPortal is a minimally compiling, but non-functioning implementor
 * of the MiniCyclingPortalInterface interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
public class BadMiniCyclingPortal implements MiniCyclingPortalInterface {

	
	/** 
	 * @return int[]
	 */
	@Override
	public int[] getRaceIds() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/** 
	 * @param name
	 * @param description
	 * @return int
	 * @throws IllegalNameException
	 * @throws InvalidNameException
	 */
	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/** 
	 * @param raceId
	 * @return String
	 * @throws IDNotRecognisedException
	 */
	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/** 
	 * @param raceId
	 * @throws IDNotRecognisedException
	 */
	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	
	/** 
	 * @param raceId
	 * @return int
	 * @throws IDNotRecognisedException
	 */
	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/** 
	 * @param raceId
	 * @param stageName
	 * @param description
	 * @param length
	 * @param startTime
	 * @param type
	 * @return int
	 * @throws IDNotRecognisedException
	 * @throws IllegalNameException
	 * @throws InvalidNameException
	 * @throws InvalidLengthException
	 */
	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/** 
	 * @param raceId
	 * @return int[]
	 * @throws IDNotRecognisedException
	 */
	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/** 
	 * @param stageId
	 * @return double
	 * @throws IDNotRecognisedException
	 */
	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/** 
	 * @param stageId
	 * @throws IDNotRecognisedException
	 */
	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	
	/** 
	 * @param stageId
	 * @param location
	 * @param type
	 * @param averageGradient
	 * @param length
	 * @return int
	 * @throws IDNotRecognisedException
	 * @throws InvalidLocationException
	 * @throws InvalidStageStateException
	 * @throws InvalidStageTypeException
	 */
	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/** 
	 * @param stageId
	 * @param location
	 * @return int
	 * @throws IDNotRecognisedException
	 * @throws InvalidLocationException
	 * @throws InvalidStageStateException
	 * @throws InvalidStageTypeException
	 */
	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/** 
	 * @param segmentId
	 * @throws IDNotRecognisedException
	 * @throws InvalidStageStateException
	 */
	@Override
	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	
	/** 
	 * @param stageId
	 * @throws IDNotRecognisedException
	 * @throws InvalidStageStateException
	 */
	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	
	/** 
	 * @param stageId
	 * @return int[]
	 * @throws IDNotRecognisedException
	 */
	@Override
	public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/** 
	 * @param name
	 * @param description
	 * @return int
	 * @throws IllegalNameException
	 * @throws InvalidNameException
	 */
	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/** 
	 * @param teamId
	 * @throws IDNotRecognisedException
	 */
	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	
	/** 
	 * @return int[]
	 */
	@Override
	public int[] getTeams() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/** 
	 * @param teamId
	 * @return int[]
	 * @throws IDNotRecognisedException
	 */
	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/** 
	 * @param teamID
	 * @param name
	 * @param yearOfBirth
	 * @return int
	 * @throws IDNotRecognisedException
	 * @throws IllegalArgumentException
	 */
	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/** 
	 * @param riderId
	 * @throws IDNotRecognisedException
	 */
	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	
	/** 
	 * @param stageId
	 * @param riderId
	 * @param checkpoints
	 * @throws IDNotRecognisedException
	 * @throws DuplicatedResultException
	 * @throws InvalidCheckpointsException
	 * @throws InvalidStageStateException
	 */
	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
			InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	
	/** 
	 * @param stageId
	 * @param riderId
	 * @return LocalTime[]
	 * @throws IDNotRecognisedException
	 */
	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/** 
	 * @param stageId
	 * @param riderId
	 * @return LocalTime
	 * @throws IDNotRecognisedException
	 */
	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/** 
	 * @param stageId
	 * @param riderId
	 * @throws IDNotRecognisedException
	 */
	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	
	/** 
	 * @param stageId
	 * @return int[]
	 * @throws IDNotRecognisedException
	 */
	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/** 
	 * @param stageId
	 * @return LocalTime[]
	 * @throws IDNotRecognisedException
	 */
	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/** 
	 * @param stageId
	 * @return int[]
	 * @throws IDNotRecognisedException
	 */
	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/** 
	 * @param stageId
	 * @return int[]
	 * @throws IDNotRecognisedException
	 */
	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eraseCyclingPortal() {
		// TODO Auto-generated method stub

	}

	
	/** 
	 * @param filename
	 * @throws IOException
	 */
	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	
	/** 
	 * @param filename
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

}
