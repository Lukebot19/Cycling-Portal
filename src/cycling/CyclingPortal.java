package cycling;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;



public class CyclingPortal implements CyclingPortalInterface {

    ArrayList<Race> Races = new ArrayList<>();
    ArrayList<Stage> Stages = new ArrayList<>();
    ArrayList<Segment> Segments = new ArrayList<>();
    ArrayList<Team> Teams = new ArrayList<>();
    ArrayList<Rider> Riders = new ArrayList<>();

    
    /** 
     * @return int[]
     */
    @Override
    public int[] getRaceIds() {
        int[] idArray = new int[Races.size()];
        for (int i = 0; i < Races.size(); i++) {
            idArray[i] = Races.get(i).getId();            
        }
        return idArray;
    }

    
    /** 
     * @param name
     * @param description
     * @return int
     * @throws InvalidNameException
     * @throws IllegalNameException
     */
    @Override
    public int createRace(String name, String description) throws InvalidNameException, IllegalNameException {
        // If the name is null, empty, has more than 30 characters, or has white spaces. Throw IllegalNameException
        if (name == null) { // Checks if name is null
            throw new InvalidNameException("Name cannot be Null"); // Throws a new InvalidNameException with the message "Name cannot be Null"
        }
        if (name.length() > 30) { // Checks if the length of the name is greater than 30
            throw new InvalidNameException("Name must be less than 30 characters"); // Throws a new InvalidNameException with the message "Name must be less than 30 characters"
        }
        if (name.contains(" ")){ //Checks if any white space is present in the provided name
            throw new InvalidNameException("Name must not contain white space"); // Throws a new InvalidNameException with the message "Name must not contain white space"
        }
        // If the name already exists in the platform. Throw IllegalNameException
        for (Race i:Races) { // For each race in races
            if (i.getName().equals(name)) { // Checks if the name of the race is equal to the name provided
                throw new IllegalNameException("Race name already exists"); // Throws a new IllegalNameException with the message "Race name already exists"
            }
        }

        Races.add(new Race(name, description)); // Creates a new race and add it to the Races ArrayList
        return Races.get(Races.size()-1).getId(); // Returns the ID of the race at the end of the list
        // return race1.getId();
    }

    
    /** 
     * @param raceId
     * @return String
     * @throws IDNotRecognisedException
     */
    @Override  
    public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
        assert raceId > 0 : "Race ID must be greater than 0"; // Throws an assertion exception if the raceId is less than 0
        Boolean found = false;
        String description = "", name = "";
        int numberOfStages = 0;
        double totalLength = 0;
        for (Race race : Races) { // Starts a loop that loops through the races in the race array list
            if (race.getId() == raceId) { // Checks if the ID of the race is equal to the raceID provided
                found = true; // Sets the found variable to true
                name = race.getName(); // Gets the name of the race
                description = race.getDescription(); // Gets the description of the race
                numberOfStages = this.getNumberOfStages(raceId); // Gets the nymber of stages in the race
                for (int stageID : this.getRaceStages(raceId)) { // Starts a for loop that loops through the stages with the given race ID
                    for (Stage stage : Stages) { // A for loop that loops through the stages in the stages array list
                        if (stage.getStageId() == stageID) { // Checks if the stage ID of the stage is equal to the provided stage ID
                            totalLength += stage.getLength(); // Adds the length of the stage to the total length variable
                        }
                    }
                }                
                break; // Breaks from the for loop
            }
        }
        if (!found) { // Checks if the found variable is set to false
            throw new IDNotRecognisedException("Race ID doesn't match a race on the system"); // Throws a new IDNotRecognised exception
        }
        else{
            return String.format("RaceId : %d, \nName: %s, \nDecription: %s, \nNumber of Stages: %d, \nTotal Length: %fKM",raceId,name,description,numberOfStages,totalLength); // Returns a formatted strring with all the information about the race
        }
    }

    
    /** 
     * @param raceId
     * @throws IDNotRecognisedException
     */
    @Override
    public void removeRaceById(int raceId) throws IDNotRecognisedException {
        assert raceId > 0 : "Race ID must be greater than 0"; // Throws an assertion exception if the raceId is less than 0
        boolean found = false; // Declares a variable found and sets it equal to false
        for (Race race : Races){  // Loops through all the races in the race array list
            if (race.getId()==raceId){ // Checks if the race id for that race is equal to the given race id
                found = true; // Sets found equal to true
                for (Stage stage : Stages){ // Loops through all the stages in the stage array list
                    if (stage.getRaceID() ==  raceId){ // Checks if the race id for that stage is equal to the given race id
                        removeStageById(stage.getRaceID()); // Removes the stage with the given race id
                    }
                }
                break; // Breaks from the for loop
            }
        }
        if (!found){ // Checks if the found variable is equal to false
            throw new IDNotRecognisedException("That Race Id is not recognised"); // Throws a new IDNotRecognisedExcception with message: 'That Race Id is not recognised'
        }
        for (int i=0; i<Races.size(); i++){ // Loops through the following code i number of times, where i is the length of the races array list
            if (Races.get(i).getId() == raceId){ // Checks if the race id for that race is equal to the given race id
                Races.remove(i); // Removes that race from the system
                break; // Breaks from the for loop
            }
        }
        
    }

    
    /** 
     * @param raceId
     * @return int
     * @throws IDNotRecognisedException
     */
    @Override
    public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
        assert raceId > 0 : "Race ID must be greater than 0"; // Throws an assertion exception if the raceId is less than 0
        boolean found = false; // Defines the found variable as a boolean with the value of False.
        for (Race race : Races) { // For each Race in races
            if (race.getId() == raceId) { // Checks if the ID of the race is equal to the race ID 
                found = true; // Sets found to True
            }
        }
        if (found) { // Checks if found is equal to True
            int count = 0; // Defines a variable count as an int and sets it to 0
            for (Stage stage : Stages) { // Starts a loop which loops through the stages in the Stages array list
                if (stage.getRaceID() == raceId) { // Checks if the race ID of the stage is equal to the race ID provided
                    count++; // Adds one to the count variable
                }
            }
            return count; // Returns count
        } else {
            throw new IDNotRecognisedException("Race ID not found"); // Throws a new IDNotRecognised exception
        }
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
            StageType type) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
        assert raceId > 0 : "Race ID must be greater than 0"; // Throws an assertion exception if the raceId is less than 0
        boolean found = false;
        for (Race race :Races) { //For each race in races
            if (race.getId() == raceId) { //Checks if the current race's id equals the provided raceId
                found = true; // Sets the found variable to true
            }
        }
        if (found == false) { // Checks if the found variable is equal to false
            throw new IDNotRecognisedException("ID of the race not found"); // Throws a new IDNotRecognised exception
        }
        for (Stage stage : Stages) { //For each stage in Stages
            if (stage.getStageName().equals(stageName)) { //Checks if the current stage's name equal to the provided stage name
                throw new IllegalNameException("Stage with name already exists"); // Throws a new IllegalNameException with the message "Stage with name already exists"
            }
        }
        if (stageName == null) { // Checks if the stageName is equal to null
            throw new InvalidNameException("Name cannot be Null"); // Throws a new InvalidNameException with the message "Name cannot be Null"
        }
        if (stageName.length() > 30) { // Checks if the stageName is less than 30 characters long
            throw new InvalidNameException("Name must be less than 30 characters"); // Throws a new InvalidNameException with the message "Name must be less than 30 characters"
        }
        if (stageName.length() == 0){ // Checks if the length of stageName is equal to 0 character
            throw new InvalidNameException("Name cannot be empty"); // Throws a new InvalidNameException with the message "Name cannot be empty"
        }
        if (length < 5) { // Checks if the legnth of the stage is less than 5km
            throw new InvalidLengthException("Stage length must be at least 5km"); // Throws a new InvalidLengthException with the message "Stage length must be at least 5km"
        }
        Stages.add(new Stage(raceId, stageName, description, length, startTime, type)); //Creates a new stage and adds it to the stage list
        
        return Stages.get(Stages.size()-1).getStageId(); // Returns the ID of the stage at the end of the stages list
    }

    
    /** 
     * @param raceId
     * @return int[]
     * @throws IDNotRecognisedException
     */
    @Override
    public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
        assert raceId > 0 : "Race ID must be greater than 0"; // Throws an assertion exception if the raceId is less than 0
        ArrayList<Stage> stageThings = new ArrayList<>(); // Creates a new ArrayList object called segmentIDs
        boolean found = false;
        for (Race Race : Races) { // For each Race in Races
            if (Race.getId() == raceId) { // If the selected race's id is equal to the specified race id
                found = true; // Sets the found variable to true
                break; // Breaks out of the for loop ound completed
            }
        }
        if (!found) { // If not found
            throw new IDNotRecognisedException("Stage ID not recognised"); // Throws an IDNotRecognised Exception
        }
        for (Stage stage: Stages) { // For stage, stage in the stages arrayList
            if (stage.getRaceID() == raceId) { // Checks if the raceId for that race is equal to the stageId
                stageThings.add(stage); // Adds the stage to the stageThings arrayList
            }
        }
        Collections.sort(stageThings); // Sorts stageThings on start time
        int[] idArray = new int[stageThings.size()]; // Creates a new array called idArray
        for (int i = 0; i < stageThings.size(); i++) { //For each stageID in the ArrayLiist
           idArray[i] = stageThings.get(i).getStageId(); // Add each stageId in stageThings to idArray array
        }
        return idArray; // Returns the stageId list
    }

    
    /** 
     * @param stageId
     * @return double
     * @throws IDNotRecognisedException
     */
    @Override
    public double getStageLength(int stageId) throws IDNotRecognisedException {
        assert stageId > 0 : "Stage ID must be greater than 0"; // Throws an assertion exception if the stageId is less than 0
        for (Stage stage : Stages) { // For each stage in Stages ArrayList
            if (stage.getStageId() == stageId) { // Checks if the stageId for that stage is equal to the stage ID provided
                return stage.getLength(); // Returns the length of the stage in KM
            }
        }
        throw new IDNotRecognisedException("Stage ID doesn't belong to a stage"); // Throws a new IDNotRecognised Exception
    }

    
    /** 
     * @param stageId
     * @throws IDNotRecognisedException
     */
    @Override
    public void removeStageById(int stageId) throws IDNotRecognisedException {
        assert stageId > 0 : "Stage ID must be greater than 0"; // Throws an assertion exception if the stageId is less than 0
        boolean found = false; // Declares a variable found to be equal to false
        for(Stage stage : Stages){ // Loops through the stages in the stages array list
            if (stage.getStageId() == stageId){ // Checks if the stageId of that stage is equal to the given stage id
                found = true; // Sets the found variable to true
                for (int i=0;i<Segments.size();i++){ // Loops through the loop i number of times where i is equal to the legnth of the segment array list
                    if(Segments.get(i).getStageId() == stageId){ // checks if the segment at that index in the array list has the sae stage id as the given stage id
                        Segments.remove(i); // Removes the segment at that index of the segment array list
                    }
                }
                break; // Breaks from the for loop
            }
        }
        if (!found){ // Checks if the found variable is equal to false
            throw new IDNotRecognisedException("That is not a valid stage ID"); // Throws a new IDNotRecognised exception with the message That is not a valid team ID
        }
        for (int i = 0; i< Stages.size(); i++){ // Loops through the stage array list the number of times of the length of the list
            if(Stages.get(i).getStageId() == stageId){ // Checks if the stage ID for that stage in the array list is equal to the given stage ID
                Stages.remove(i); // Removes the stage from the array list
                break; // Breaks from the for loop
            }
        }
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
            Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        assert stageId > 0 : "Stage ID must be greater than 0"; // Throws an assertion exception if the stageId is less than 0
        boolean found = false;
        for (Stage stage: Stages) { // Starts a for loop to loop through the stages in the stage array list
            if (stage.getStageId() == stageId) { // Checks if the stage ID of the stage is equal to the given stage ID
                found = true; // Sets the found variable to be true
                if (stage.getLength() < location) { // If stage length is less than the distance to the end of the segment
                    throw new InvalidLocationException("The location must be less than the length of the stage"); // Throws a new InvalidLocationException witth the message: The location must be less than the length of the stage 
                }
                if (stage.getType() == StageType.TT){ // If stage type is TT from enum StageType
                    throw new InvalidStageTypeException("Time-trial stages cannot contain any segment"); // Throws a new InvalidStageType Exception with the message: Time-trial stages cannot contain any segment
                }
                if (stage.getWaitingForResults()) { // If stage state is waiting for results
                    throw new InvalidStageStateException("Stage: " + stage.getStageName() + " is waiting for a result"); // Throws a new InvaldStageState exception with the message: "Stage: " + stage.getStageName() + " is waiting for a result"
                }
                

                break; // Breaks from the for loop
            }
        }
        if (!found) { // If stage with given stageId is not found
            throw new IDNotRecognisedException("Stage ID doesn't match any stages"); // Throws a new IDNotRecognisedException with the message: Stage ID doesn't match any stages
            
        }        
        Segments.add(new Segment(stageId, location, type, averageGradient, length)); //Creates a new segment and adds it to the segment list

        return Segments.get(Segments.size()-1).getSegmentId(); // Returns the ID of the segment created
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
    public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException{
        assert stageId > 0 : "Stage ID must be greater than 0"; // Throws an assertion exception if the stageId is less than 0
        boolean found = false;
        for (Stage stage: Stages) { // Starts a for loop to loop through the stages in the stage array list
            if (stage.getStageId() == stageId) { // Checks if the stage ID of the stage is equal to the given stage ID
                found = true; // Sets the found variable to be true
                if (stage.getLength() < location) { // If stage length is less than the distance to the end of the segment
                    throw new InvalidLocationException("The location must be less than the length of the stage"); // Throws a new InvalidLocationException witth the message: The location must be less than the length of the stage 
                }
                if (stage.getType() == StageType.TT){ // If stage type is TT from enum StageType
                    throw new InvalidStageTypeException("Time-trial stages cannot contain any segment"); // Throws a new InvalidStageType Exception with the message: Time-trial stages cannot contain any segment
                }
                if (stage.getWaitingForResults()) { // If stage state is waiting for results
                    throw new InvalidStageStateException("Stage: " + stage.getStageName() + " is waiting for a result"); // Throws a new InvaldStageState exception with the message: "Stage: " + stage.getStageName() + " is waiting for a result"
                }
                break; // Breaks from the for loop
            }
        }
        if (!found) { // If stage with given stageId is not found
            throw new IDNotRecognisedException("Stage ID doesn't match any stages"); // Throws a new IDNotRecognisedException with the message: Stage ID doesn't match any stages
            
        }
        
        Segments.add(new Segment(stageId, location, SegmentType.SPRINT)); //Creates a new segment and adds it to the segment list
        
        return Segments.get(Segments.size()-1).getSegmentId(); // Returns the ID of the segment
    }

    
    /** 
     * @param segmentId
     * @throws IDNotRecognisedException
     * @throws InvalidStageStateException
     */
    @Override
    public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
        assert segmentId > 0 : "Segment ID must be greater than 0"; // Throws an assertion exception if the segmentId is less than 0
        boolean found = false; // Declares a variable called found and sets it to false
        int theStageId = 0; // Declares a new variable called theStageId
        for (Segment segment : Segments){ // For each segment in the segment array list
            if (segment.getSegmentId() == segmentId){ // Checks if the segment id for that segment is equal to the given segment id
                found = true; // Sets the found variable to true
                theStageId = segment.getStageId(); // Gets the stage if for the segment
            }
        }
        if (!found){ // If found is set to false
            throw new IDNotRecognisedException("Segment with that Id not found"); // throws a new IDNotRecognisedException with the message "Segment with that Id not found"
        }
        for (Stage stage : Stages){ // for each stage in the stages array list
            if (stage.getStageId() == theStageId){ // Checks if the stage id for that stage is equal to the theStageId variable
                if (stage.getWaitingForResults()){ // Checks if the stage is waiting for a reult
                    throw new InvalidStageStateException("Stage is waiting for results"); // Throws a new InvalidStageStateException with the message "Stage is waiting for results"
                }
            }
        }
        for (int i=0; i<Segments.size(); i++){ // Loops through the following code i number of times where i is the length of the segment array list
            if (Segments.get(i).getSegmentId() == segmentId) { // Checks if the segment id for that segment is equal to the given segment id
                Segments.remove(i); // Removes the segment from the segment array list
                break; // Breaks from the for loop
            }
        }
        
    }

    
    /** 
     * @param stageId
     * @throws IDNotRecognisedException
     * @throws InvalidStageStateException
     */
    @Override
    public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
        assert stageId > 0 : "Stage ID must be greater than 0"; // Throws an assertion exception if the stageId is less than 0
        boolean found = false;
        for (Stage stage: Stages) { // For each stage in Stages
            if (stage.getStageId() == stageId) { // If curren stage's ID is equal to providedd stageID
                if (stage.getWaitingForResults()) { // If current stage is waiting for results
                    throw new InvalidStageStateException("Stage is already waiting for results"); // Throws a new INvalidStageStateException with the message Stage is already waiting for result
                }
                stage.setWaitingForResults(true); // Sets current stage's WaitingForResults variable to true
                found = true; // Sets the found variable to true
            }
        }
        if (!found) { // Checks if the found variable is set to false
            throw new IDNotRecognisedException("Stage ID is not recognized"); // Throws a new IDNotRecognised exception with the message Stage ID is not recognized
        }
    }

    
    /** 
     * @param stageId
     * @return int[]
     * @throws IDNotRecognisedException
     */
    @Override
    public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
        assert stageId > 0 : "Stage ID must be greater than 0"; // Throws an assertion exception if the stageId is less than 0
        ArrayList<Segment> segmentThings = new ArrayList<>(); // Creates a new ArrayList object called segmentIDs
        boolean found = false;
        for (Stage stage : Stages) { // For each Stage in Stages
            if (stage.getStageId() == stageId) { // If the selected stage's id is equal to the specified stage id
                found = true; // Sets the found variable to true
                break; // Breaks out of the for loop ound completed
            }
        }
        if (!found) { // If the found variable is set to false
            throw new IDNotRecognisedException("Stage ID not recognised"); // Throws an IDNotRecognised Exception
        }
        for (Segment segment: Segments) { // For each segment in the Segments arrayList
            if (segment.getStageId() == stageId) { //Checks if the segmentId for that segment is equal to the stageId
                segmentThings.add(segment); // Adds the segmentId to the segmentIds ArrayList
            }
        }
        Collections.sort(segmentThings); // Sorts the arrayList on segment location
        int[] idArray = new int[segmentThings.size()]; // Creates a new array called idArray
        for (int i = 0; i < segmentThings.size(); i++) { //For each segment in the ArrayList
           idArray[i] = segmentThings.get(i).getSegmentId(); // Add each segmentId in segmentThings to idArray array
        }
        return idArray; // Returns the segmentId list
    }

    
    /** 
     * @param name
     * @param description
     * @return int
     * @throws InvalidNameException
     * @throws IllegalNameException
     */
    @Override
    public int createTeam(String name, String description) throws InvalidNameException, IllegalNameException {
        
        if (name == null) { // Checks if name is null
            throw new InvalidNameException("Name cannot be Null"); // Throws a new InvalidNameException with the message "Name cannot be Null"
        }
        if (name.length() > 30) { // Checks if the length of the name is greater than 30
            throw new InvalidNameException("Name must be less than 30 characters"); // Throws a new InvalidNameException with the message "Name must be less than 30 characters"
        }
        if (name.contains(" ")){ //Checks if any white space is present in the provided name
            throw new InvalidNameException("Name must not contain white space"); // Throws a new InvalidNameException with the message "Name must not contain white space"
        }
        if (name.equals("")){
            throw new InvalidNameException("Name cannot be empty"); // Throws a new InvalidNameException with the message "Name cannot be empty"
        }
        // If the name already exists in the platform. Throw IllegalNameException
        for (Team team: Teams) { // For each race in races
            if (team.getName().equals(name)) { // Checks if the name of the race is equal to the name provided
                throw new IllegalNameException("Team name already exists"); // Throws a new IllegalNameException with the message "Team name already exists"
            }
        }

        Teams.add(new Team(name, description)); //Creates a new team and adds it to the team list
        return Teams.get(Teams.size()-1).getTeamId(); // Returns the ID of the Team at the end of the teams list;
    }

    
    /** 
     * @param teamId
     * @throws IDNotRecognisedException
     */
    @Override
    public void removeTeam(int teamId) throws IDNotRecognisedException {
        assert teamId > 0 : "Team ID must be greater than 0"; // Throws an assertion exception if the teamId is less than 0
        boolean found = false; // Declares a variable found to be equal to false
        for(Team team : Teams){ // Loops through the teams in the teams array list
            if (team.getTeamId() == teamId){ // Checks if the teamId of that team is equal to the given team Id
                found = true; // Sets the found variable to true
                for (Rider rider : Riders){ // Loops through the riders in the riders array list
                    if (rider.getTeamId() == teamId){ // Checks if the team id for that rider is equal to the given team id
                        removeRider(rider.getId()); // Removes the rider from the syste
                    }
                }
            }
        }
        if (!found){ // Checks if the found variable is equal to false
            throw new IDNotRecognisedException("That is not a valid team ID"); // Throws a new IDNotRecognised exception with the message That is not a valid team ID
        }
        for (int i = 0; i< Teams.size(); i++){ // Loops through the teams array list the number of times of the length of the list
            if(Teams.get(i).getTeamId() == teamId){ // Checks if the team ID for that team in the array list is equal to the given team ID
                Teams.remove(i); // Removes the team from the array list
                break; // Breaks from the for loop
            }
        }
        
    }

    
    /** 
     * @return int[]
     */
    @Override
    public int[] getTeams() {
        ArrayList<Integer> TeamIDs = new ArrayList<>();
        for (Team team : Teams) {
            TeamIDs.add(team.getTeamId());
        }
        int[] idArray = new int[TeamIDs.size()];
        for (int i = 0; i < TeamIDs.size(); i++) { //For each teamID in the ArrayLiist
            idArray[i] = TeamIDs.get(i); // Add each value in teamIDs to idArray array
        }
        return idArray;
    }

    
    /** 
     * @param teamId
     * @return int[]
     * @throws IDNotRecognisedException
     */
    @Override
    public int[] getTeamRiders(int teamId) throws IDNotRecognisedException{
        assert teamId > 0 : "Team ID must be greater than 0"; // Throws an assertion exception if the teamId is less than 0
        ArrayList<Integer> riderIDs = new ArrayList<>(); // Creates a new ArrayList object called riderIDs
        boolean found = false;
        for (Team team : Teams) { // For each team in Teams
            if (team.getTeamId() == teamId) { // If the selected riders id is equal to the specified rider id
                found = true; // Sets the found variable to true
                break; // Breaks out of the for loop ound completed
            }
        }
        if (!found) {
            throw new IDNotRecognisedException("Team ID not recognised"); // Throws an IDNotRecognised Exception
        }
        for (Rider rider: Riders) {
            if (rider.getTeamId() == teamId) { //Checks if the riderId for that rider is equal to the riderId
                riderIDs.add(rider.getId()); // Adds the riderId to the riderIds ArrayList
            }
        }
        int[] idArray = new int[riderIDs.size()]; // Creates a new array called idArray
        for (int i = 0; i < riderIDs.size(); i++) { //For each riderID in the ArrayLiist
            idArray[i] = riderIDs.get(i); // Add each value in riderIDs to idArray array
        }
        return idArray; // Returns the riderId list
    }

    
    /** 
     * @param teamId
     * @param name
     * @param yearOfBirth
     * @return int
     * @throws IDNotRecognisedException
     * @throws IllegalArgumentException
     */
    @Override
    public int createRider(int teamId, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException {
        assert teamId > 0 : "Team ID must be greater than 0"; // Throws an assertion exception if the teamId is less than 0
        boolean found = false;
        if (name == null) { // Checks if name is null
            throw new IllegalArgumentException("Name cannot be Null"); // Throws a new InvalidNameException with the message "Name cannot be Null"
        }
        if (yearOfBirth<1900) {
            throw new IllegalArgumentException("Year of birth must be greater than 1900"); // Throws a new InvalidNameException with the message "Year of birth mus be greater than 1900"
        }
        // If the name already exists in the platform. Throw IllegalNameException
        for (Team team: Teams) { // For each race in races
            if (team.getTeamId() == (teamId)) { // Checks if the name of the race is equal to the name provided
                found = true; 
            }
        }
        if (!found) {
            throw new IDNotRecognisedException("No team with that ID was found"); // Throws a new InvalidNameException with the message No team with that ID was found
        }
        Riders.add(new Rider(teamId, name, yearOfBirth)); //Creates a new rider and adds it to the riders list
        return Riders.get(Riders.size()-1).getId(); // Returns the ID of the Rider at the end of the rider list;
    }

    
    /** 
     * @param riderId
     * @throws IDNotRecognisedException
     */
    @Override
    public void removeRider(int riderId) throws IDNotRecognisedException {
        assert riderId > 0 : "Rider ID must be greater than 0"; // Throws an assertion exception if the riderId is less than 0
        boolean found = false; // Creates a variable called found and sets it to false
        for (Rider rider : Riders){ // Loops through the riders in the riders arrayList
            if (rider.getId() == riderId){ // Checks if the riderId is equal to the Id of the rider in the loop
                found = true; // Sets the found variable to true
                for (Stage stage : Stages){ // For each stage in the stage array list
                    stage.removeRiderResults(riderId); // Removes the riders results from the hash map in that stage
                }
                break;
            }
        }
        if (!found){ // Checks if the found variable is equal to false
            throw new IDNotRecognisedException("That is not a valid rider ID"); // Throws a new IDNotRecognised exception with the message That is not a valid rider ID
        }
        for (int i = 0; i< Riders.size(); i++){ // Loops through the riders array list the number of times of the length of the list
            if(Riders.get(i).getId() == riderId){ // Checks if the rider ID for that rider in the array list is equal to the given rider ID
                Riders.remove(i); // Remoes the rider from the array list
                break; // Breaks from the for loop
            }
        }
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
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints) throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException, InvalidStageStateException {
        assert riderId > 0 : "Rider ID must be greater than 0"; // Throws an assertion exception if the riderId is less than 0
        assert stageId > 0 : "Stage ID must be greater than 0"; // Throws an assertion exception if the stageId is less than 0
        boolean found = false; // Creates a variable called found and sets it to false
        for (Rider rider : Riders) { // Loops through the Riders ArrayList
            if (rider.getId() == riderId){ // Checks if the riderId is equal to the Id of the rider in the loop
                found = true; // Sets the found variable to true
            }
        }
        if (!found) { // Checks if the found variable is set to false
            throw new IDNotRecognisedException("That is not a valid rider ID"); // Throws a new IDNotRecognised exception with the message That is not a valid rider ID
        }        
        found = false; // Sets found to falsee
        for (Stage stage: Stages) { // Loops through the stage ArrayList
            if (stage.getStageId() == stageId) { //Checks if the stageId is equal to the Id of the stage in the loop
                found = true; // Sets found to true
                if (stage.getStageTimes(riderId)!=null) { // Checks if the stage times array at that riders Id is null
                    throw new DuplicatedResultException("Rider already has a result"); // Throws a new DuplicateResult exception with the message Rider already has a result
                }
                if (checkpoints.length != getStageSegments(stageId).length+2) { // Checks if the length of the input checkpoints is not equal to the number of segments in the stage + 2
                    throw new InvalidCheckpointsException("Incorrect number of times for that stage"); // Throws a new InvalidCheckpoints exception with the message Incorrect number of times for that stage
                }
                if (!stage.getWaitingForResults()){ // Checks if the stage is waiting for results
                    throw new InvalidStageStateException("Stage is not waiting for results"); // Throws a new InvalidStageState exception with the message Stage is not waiting for results
                }
                stage.setStageTimes(riderId, checkpoints); // Sets the stage times for the given rider
            }
        }
        
        if (!found) { // Checks if the found variable is equal to false
            throw new IDNotRecognisedException("That is not a valid stage ID"); // Throws a new IDNotRecognised exception with the message That is not a valid stage ID
        }
    }

    
    /** 
     * @param stageId
     * @param riderId
     * @return LocalTime[]
     * @throws IDNotRecognisedException
     */
    @Override
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        assert riderId > 0 : "Rider ID must be greater than 0"; // Throws an assertion exception if the riderId is less than 0
        assert stageId > 0 : "Stage ID must be greater than 0"; // Throws an assertion exception if the stageId is less than 0
        boolean found = false; // Creates a variable called found and sets it to false
        for (Rider rider : Riders){ // Loops through the riders in the riders arrayList
            if (rider.getId() == riderId){ // Checks if the riderId is equal to the Id of the rider in the loop
                found = true; // Sets the found variable to true
            }
        }
        if (!found){ // Checks if the found variable is equal to false
            throw new IDNotRecognisedException("That is not a valid rider ID"); // Throws a new IDNotRecognised exception with the message That is not a valid rider ID
        }
        found = false; // Sets the found variable to false
        for (Stage stage : Stages) { // For each Stage in Stages
            if (stage.getStageId() == stageId) { // If selected stageId equals the given stage id
                LocalTime[] times = stage.getStageTimes(riderId); // Times array is given the array from getStageTimes
                if (times == null){ // Checks if the times variable is equal to null
                    return new LocalTime[0]; // Returns a new LocalTime array of length 0
                }
                LocalTime[] newTimes = new LocalTime[times.length]; // Creates a new array with the length of the times array
                for (int i = 0; i<times.length-1; i++){ // Loops for the legnth of the times array
                    newTimes[i] = times[i+1]; // Sets the newTimes array at possition i to the value held at the times array at position i + 1
                }                
                newTimes[times.length-1] = times[times.length-1].minusNanos(times[0].toNanoOfDay()); // Appends the LocalTime start value minus the Localtime finish value to give the elapsed time at the end of the array
                return newTimes; // Returns the newTimes array
            }
        }
        if (!found){ // Checks if the found variable is equal to false
            throw new IDNotRecognisedException("That is not a valid stage ID"); // Throws a new IDNotRecognised exception with the message That is not a valid stage ID
        }
        return null; // Returns null
    }

    
    /** 
     * @param stageId
     * @param riderId
     * @return LocalTime
     * @throws IDNotRecognisedException
     */
    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
        assert riderId > 0 : "Rider ID must be greater than 0"; // Throws an assertion exception if the riderId is less than 0
        assert stageId > 0 : "Stage ID must be greater than 0"; // Throws an assertion exception if the stageId is less than 0
        boolean found = false; // Creates a variable called found and sets it to false
        for (Rider rider : Riders){ // Loops through the riders in the riders arrayList
            if (rider.getId() == riderId){ // Checks if the riderId is equal to the Id of the rider in the loop
                found = true; // Sets the found variable to true
                break;
            }
        }
        if (!found){ // Checks if the found variable is equal to false
            throw new IDNotRecognisedException("That is not a valid rider ID"); // Throws a new IDNotRecognised exception with the message That is not a valid rider ID
        }
        found = false; // Sets the found variable to false
        for (Stage stage : Stages){ // Loops through the stages in the stages arraylist
            if (stage.getStageId() == stageId){ // Checks if the stageId is equal to the stageId given
                found = true; // Sets the found variable to true
                stage.sortHashMap(); // Sorts the stage hash map
                LocalTime[] timeArray = getRiderResultsInStage(stageId, riderId); // Gets the riders results for the given stage and puts them into tthe time array
                LocalTime elapsedTime = timeArray[timeArray.length-1]; // Gets the elapsed time which is stored in the last index of the array
                int rankIncr = 1; // Sets the rankIncrement variable to 1
                int rank = stage.getRank(riderId); // Gets that riders rank in the stage
                if (rank != 1){ // Checks if the riders rank is not equal to 1
                    while(rank > rankIncr && elapsedTime.minusNanos(((getRiderResultsInStage(stageId, stage.getRiderIdFromRank(rank-rankIncr)))[timeArray.length-1]).toNanoOfDay()).toNanoOfDay() < 1000000000) { // While the rank of the rider is greater than the rank increment and the difference between the elapsed time of the current and next rider is less than 1 second
                        elapsedTime = getRiderResultsInStage(stageId, stage.getRiderIdFromRank(rank-rankIncr))[timeArray.length-1]; // Sets the elapsed time to the elapsed time of that rider
                        rankIncr++; // increments the rankIncr by one
                    }
                    
                }
                return elapsedTime; // Returns the elapsed time

            }
        }
        if (!found){ // Checks if the found variable is equal to false
            throw new IDNotRecognisedException("That is not a valid stage ID"); // Throws a new IDNotRecognised exception with the message That is not a valid stage ID
        }
        return null; // Returns null
    }

    
    /** 
     * @param stageId
     * @param riderId
     * @throws IDNotRecognisedException
     */
    @Override
    public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        assert riderId > 0 : "Rider ID must be greater than 0"; // Throws an assertion exception if the riderId is less than 0
        assert stageId > 0 : "Stage ID must be greater than 0"; // Throws an assertion exception if the stageId is less than 0
        boolean found = false; // Creates a variable called found and sets it to false
        for (Rider rider : Riders){ // Loops through the riders in the riders arrayList
            if (rider.getId() == riderId){ // Checks if the riderId is equal to the Id of the rider in the loop
                found = true; // Sets the found variable to true
                break;
            }
        }
        if (!found){ // Checks if the found variable is equal to false
            throw new IDNotRecognisedException("That is not a valid rider ID"); // Throws a new IDNotRecognised exception with the message That is not a valid rider ID
        }
        found = false; // Sets the variable found to false
        for (Stage stage : Stages){ // loops through all the stages in the stages array list
            if (stage.getStageId() == stageId){ // Checks if the stage ID is equal to the given stage ID
                found = true; // Sets the found variable to true
                stage.removeRiderResults(riderId); // Removes the riders results from the hashmap
            }
        }
        if (!found){ // Checks if the found variable is equal to false
            throw new IDNotRecognisedException("That is not a valid stage ID"); // Throws a new IDNotRecognised exception with the message That is not a valid stage ID
        }

        
    }

    
    /** 
     * @param stageId
     * @return int[]
     * @throws IDNotRecognisedException
     */
    @Override
    public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
        assert stageId > 0 : "Stage ID must be greater than 0"; // Throws an assertion exception if the stageId is less than 0
        boolean found = false; // Creates a found variable and sets it to false
        int count = 0; // Creates a count variable and sets it to 0
        for (Stage stage : Stages){ // Loops through the stages in the stages array list
            if (stage.getStageId() == stageId){ // Checks if the stage ID is equal to the given stageId
                found = true; // Sets the found variable to true     
                Stages.get(count).sortHashMap(); // Sorts the hashmap in the stage 
                HashMap<Integer, LocalTime[]> map = stage.getHashMap(); // Gets the hashmap from the stage and sets the map variable to it
                int[] riderIds = new int[map.size()]; // Creates a riderIds array with the size of the map 
                int count2 = 0; // Creates a count2 variable and sets it to 0
                for (int key : map.keySet()){ // Loops through key key in the key set of the map
                    riderIds[count2] = key; // Inserts the key into the riderIds array at the index of count2
                    count2++; // Increments the count2 variable by 1
                }
                return riderIds; // Returns the riderIds
            }
            count++; // Increments the count variable by 1
        }
        if (!found){ // Checks if the found variable is equal to false
            throw new IDNotRecognisedException("StageId not recognised"); // Throws a new IDNotRecognisedException with the message "StageId not recognised"
        }
        
        return null; // returns null
    }

    
    /** 
     * @param stageId
     * @return LocalTime[]
     * @throws IDNotRecognisedException
     */
    @Override
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
        assert stageId > 0 : "Stage ID must be greater than 0"; // Throws an assertion exception if the stageId is less than 0
        boolean found = false; // creates a found variable and sets it to false
        int count = 0; // Creates a count variable and sets it to 0
        for (Stage stage : Stages){ // Loops through the stages in the stages array list
            if (stage.getStageId() == stageId){ // Checks if the stage Id of that stage is equal to the given stageID
                found = true; // Sets the found variable to true          
                Stages.get(count).sortHashMap(); // Sorts the results hashmap of that stage
                HashMap<Integer, LocalTime[]> map = stage.getHashMap(); // Creates a new hashmap called map and sets it equal to the sorted hashmap of the stage
                LocalTime[] finishTimes = new LocalTime[map.size()]; // Creates a new localtime array with the size of the map hashmap
                int count2 = 0; // Creates a count2 variable and sets it equal to 0
                for (LocalTime[] value : map.values()){ // loops through the values of the map values
                    finishTimes[count2] = value[value.length-1]; // Sets the finish times array at the index of count2 to the value at the last index of the value variable
                    count2++; // Adds one to the count2 variable
                }
                return finishTimes; // Returns the finishTimes array
            }
            count++; // Adds one to the count variable
        }
        if (!found){ // Checks if the found variable is equal to false
            throw new IDNotRecognisedException("StageId not recognised"); // Throews a new IDNotRecognisedException with the message "StageId not recognised"
        }
        return null; // Return null
    }

    
    /** 
     * @param stageId
     * @return int[]
     * @throws IDNotRecognisedException
     */
    @Override
    public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
        assert stageId > 0 : "Stage ID must be greater than 0"; // Throws an assertion exception if the stageId is less than 0
        boolean found = false; // Creates a found variable and sets it equal to false
        StageType type = null; // Creates a type variable with type StageType and sets it equal to null
        HashMap<Integer, LocalTime[]> timesMap = new HashMap<Integer,LocalTime[]>(); // Creates a new hashmap called timesMap
        LinkedHashMap <Integer, Integer> pointsMap = new LinkedHashMap<Integer,Integer>(); // Creates a new Linkedhashmap called pointsMap

        for (Stage stage : Stages){ // loops through all the stages in the stages array list
            if (stage.getStageId() == stageId){ // Checks if the stage id of that stage is equal to the given stageId
                found = true; // Sets the found variable to true
                stage.sortHashMap(); // Sorts the hash map of that stage
                timesMap = stage.getHashMap(); // Gets the sorted hash map and sets the timeMap variable to it
                type = stage.getType(); // Sets the type variable to the type of that stage
                for (int key : timesMap.keySet()){ // Loops through the keys in the key set of the timesMap
                    pointsMap.put(key,0); // Adds that key to the pointsMap
                }

                switch (type) {
                    case FLAT: // If type is FLAT
                        int[] flatPoints = {50,30,20,18,16,14,12,10,8,7,6,5,4,3,2}; // Sets the flatPoints variable to the points provided
                        for (Rider rider : Riders){ // Loops through the riders in the riders array list
                            if (pointsMap.containsKey(rider.getId()) && stage.getRank(rider.getId()) <= 15){ // Checks if the pointsMap contains the rider id and the stage rank of the rider is less than or equal to 15
                                pointsMap.put(rider.getId(), flatPoints[stage.getRank(rider.getId())-1]); // Adds the rider id and the riders rank to the pointsMap
                            }
                        }
                        break; // breaks from the loop
                    case TT: // If type is TT
                        int[] ttPoints = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1}; // Sets the ttPoints variable to the points provided
                        for (Rider rider : Riders){ // Loops through the riders in the riders array list
                            if (pointsMap.containsKey(rider.getId()) && stage.getRank(rider.getId()) <= 15){ // Checks if the pointsMap contains the rider id and the stage rank of the rider is less than or equal to 15
                                pointsMap.put(rider.getId(), ttPoints[stage.getRank(rider.getId())-1]); // Adds the rider id and the riders rank to the pointsMap
                            }
                        }
                        break; // Breaks from the loop
                    case MEDIUM_MOUNTAIN: // If the type is MEDIUM_MOUNTAIN
                        int[] mmPoints = {30,25,22,19,17,15,13,11,9,7,6,5,4,3,2}; // Sets the mmPoints variable to the points provided
                        for (Rider rider : Riders){ // Loops through the riders in the riders array list
                            if (pointsMap.containsKey(rider.getId()) && stage.getRank(rider.getId()) <= 15){ // Checks if the pointsMap contains the rider id and the stage rank of the rider is less than or equal to 15
                                pointsMap.put(rider.getId(), mmPoints[stage.getRank(rider.getId())-1]); // Adds the rider id and the riders rank to the pointsMap
                            }
                        }
                        break; // Breaks from the loop
                    default: // If the type is something else
                        int[] hmPoints = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1}; // Sets the hmPoints variable to the points provided
                        for (Rider rider : Riders){ // Loops through the riders in the riders array list
                            if (pointsMap.containsKey(rider.getId()) && stage.getRank(rider.getId()) <= 15){ // Checks if the pointsMap contains the rider id and the stage rank of the rider is less than or equal to 15
                                pointsMap.put(rider.getId(), hmPoints[stage.getRank(rider.getId())-1]); // Adds the rider id and the riders rank to the pointsMap
                            }
                        }
                        break; // breaks from the loop
                }
                for (Segment segment : Segments){ // Loops through the segments in the segment array list
                    if (stageId == segment.getStageId()) { // Checks if the stage id is equal to the given stage id
                        SegmentType segmentType = segment.getType(); // sets the segmentType variable to the type of the segment
                        if (segmentType == SegmentType.SPRINT){ // Checks if the segmet type is equal to SPRINT
                            for (Rider rider : Riders){ // loops through the riders in the riders array list
                                if (pointsMap.containsKey(rider.getId()) && stage.getRank(rider.getId()) <= 15){ // Checks if the pointsMap contains the rider id and the stage rank for that rider is less than or equal to 15
                                    int[] spPoints = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1}; // Sets the spPoints variable to the points provided
                                    pointsMap.put(rider.getId(), pointsMap.get(rider.getId()) + spPoints[stage.getRank(rider.getId())-1]); // Adds the rider id and riders points to the pointsMap
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!found) { // Checks if the found variable is false
            throw new IDNotRecognisedException("Stage ID is not recognised"); // Throws a new IDNotRecognisedException with the message "Stage ID is not recognised"
        }
        int[] pointsArray = new int[pointsMap.size()]; // Creates a new pointsArray with the size of the pointsMap
        int count = 0; // Sets the count variable to 0
        for (int value : pointsMap.values()) { // Loops through the values in the pointsMap value set
            pointsArray[count] = value; // Sets the value of the pointsArray at the index of count to this variable
            count++; // Increments the count variable by one
        }
        return pointsArray; // Returns the pointsArray
    }

    
    /** 
     * @param stageId
     * @return int[]
     * @throws IDNotRecognisedException
     */
    @Override
    public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
        assert stageId > 0 : "Stage ID must be greater than 0"; // Throws an assertion exception if the stageId is less than 0
        boolean found = false; // Creates a variable callled found and sets it to false
        HashMap<Integer, LocalTime[]> timesMap = new HashMap<Integer,LocalTime[]>(); // Creates a new HashMap called timesMap
        LinkedHashMap <Integer, Integer> pointsMap = new LinkedHashMap<Integer,Integer>(); // Creates a new LinkedHashMap called pointsMap
        for (Stage stage : Stages){ // Loops through the stages in the stages array list
            if (stage.getStageId() == stageId){ // Checks if the stage id of that stage is equal to the given stage id
                found = true; // Sets found to true
                stage.sortHashMap(); // Sorts the results hashmap of that stage
                timesMap = stage.getHashMap(); // Sets the timeMap to the hashmap of that stage
                for (int key : timesMap.keySet()){ // Loops through the keys in the timeMap key set
                    pointsMap.put(key,0); // Adds the key to the pointsMap
                }
                for (Segment segment : Segments) { // Loops through the segments in the segments arrat list
                    if (segment.getStageId() == stageId){ // Checks if the stage id of that segment is equal to the given stage id
                        if (segment.getType() != SegmentType.SPRINT){ // Checks if the segment type is SPRINT
                            switch (segment.getType()) {
                                case C1: // If the segment type is C1
                                    int[] c1Points = {10,8,6,4,2,1}; // Sets the c1Points variable to the points provided
                                    for (Rider rider : Riders){ // Loops through the riders in the riders array list
                                        if (pointsMap.containsKey(rider.getId()) && stage.getRank(rider.getId()) <= 6){ // Checks if the pointsMap contains the rider id and the riders rank for that stage is less than or equal to 6
                                            pointsMap.put(rider.getId(), pointsMap.get(rider.getId()) + c1Points[stage.getRank(rider.getId())-1]); // Adds the rider id and the riders points to the pointsMap
                                        }
                                    }
                                    break; // Breaks from the switch case
                                case C2:// If the segment type is C2
                                    int[] c2Points = {5,3,2,1}; // Sets the c2Points variable to the points provided
                                    for (Rider rider : Riders){ // Loops through the riders in the riders array list
                                        if (pointsMap.containsKey(rider.getId()) && stage.getRank(rider.getId()) <= 4){ // Checks if the pointsMap contains the rider id and the riders rank for that stage is less than or equal to 4
                                            pointsMap.put(rider.getId(), pointsMap.get(rider.getId()) + c2Points[stage.getRank(rider.getId())-1]); // Adds the rider id and the riders points to the pointsMap
                                        }
                                    }
                                    break; // Breaks from the switch case
                                case C3: // If the segment type is c3
                                    int[] c3Points = {2,1};  // Sets the c3Points variable to the points provided
                                    for (Rider rider : Riders){ // Loops through the riders in the riders array list
                                        if (pointsMap.containsKey(rider.getId()) && stage.getRank(rider.getId()) <= 2){ // Checks if the pointsMap contains the rider id and the riders rank for that stage is less than or equal to 2
                                            pointsMap.put(rider.getId(), pointsMap.get(rider.getId()) + c3Points[stage.getRank(rider.getId())-1]); // Adds the rider id and the riders points to the pointsMap
                                        }
                                    }
                                    break; // Breaks from the switch case
                                case C4: // If the segment type is c4
                                    int[] c4Points = {1}; // Sets the c4Points variable to the points provided
                                    for (Rider rider : Riders){ // Loops through the riders in the riders array list
                                        if (pointsMap.containsKey(rider.getId()) && stage.getRank(rider.getId()) <= 1){ // Checks if the pointsMap contains the rider id and the riders rank for that stage is less than or equal to 1
                                            pointsMap.put(rider.getId(), pointsMap.get(rider.getId()) + c4Points[stage.getRank(rider.getId())-1]); // Adds the rider id and the riders points to the pointsMap
                                        }
                                    }
                                    break; // Breaks from the switch case
                                default: // If the type is something else
                                    int[] hcPoints = {20,15,12,10,8,6,4,2}; // Sets the hcPoints variable to the points provided
                                    for (Rider rider : Riders){ // Loops through the riders in the riders array list
                                        if (pointsMap.containsKey(rider.getId()) && stage.getRank(rider.getId()) <= 8){ // Checks if the pointsMap contains the rider id and the riders rank for that stage is less than or equal to 8
                                            pointsMap.put(rider.getId(), pointsMap.get(rider.getId()) + hcPoints[stage.getRank(rider.getId())-1]); // Adds the rider id and the riders points to the pointsMap
                                        }
                                    }
                                    break; // Breaks from the switch case
                            }
                        }
                    }
                }
            }
        }
        if (!found){ // If the found variable is equal to false
            throw new IDNotRecognisedException("Stage ID is not recognised"); // Throws a new IDNotRecognisedException with the message "Stage ID is not recognised"
        }
        int[] pointsArray = new int[pointsMap.size()]; // Creates a new pointsArray with type int and size of the pointsMap
        int count = 0; // Create a variable called count with value 0
        for (int value : pointsMap.values()) { // Loops through values in the values set of the pointsMap
            pointsArray[count] = value; // Sets the value of pointsArray at the index of count to this value
            count++; // Increments the count variable by 1
        }
        return pointsArray; // Return the pointsArray
    }

    @Override
    public void eraseCyclingPortal() {
        Races.get(0).clearNumberOfRaces(); // Sets the number of races in the system to 0
        Stages.get(0).clearNumberOfStages(); // Sets the number of stages in the system to 0
        Stages.get(0).clearNumberOfStages(); // Clears stageTimes and tempStageTimes
        Segments.get(0).clearNumberOfSegments();  // Sets the number of segments in the system to 0
        Riders.get(0).clearNumberOfRiders(); // sets the number of riders in the system to 0
        Teams.get(0).clearNumberOfTeams(); // Sets the number of teams in the system to 0
        Races.clear(); // Clears the Races array list
        Stages.clear(); // Clears the Stages array list
        Segments.clear(); // Clears the Segments array list
        Riders.clear(); // Clears the Riders array list
        Teams.clear(); // Clears the Teams array list
    }

    
    /** 
     * @param filename
     * @throws IOException
     */
    @Override
    public void saveCyclingPortal(String filename) throws IOException {
        try {
            //Creating FileOutputStream object.
            File file = new File(filename+".ser"); // Creates new File object with the name of the supplied filename + ".ser"
            FileOutputStream fos = 
            new FileOutputStream(file);
            //Creating ObjectOutputStream object.
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            //write object.
            oos.writeObject(this);                        

            //close streams.
            oos.close();
            fos.close();
           
	         
	      }catch(IOException e)
	      {
	          throw new IOException("There was a problem when trying to save to the file"); // Throws new IOException with message "There was a problem when trying to save to the file"
	      }
    }

    
    /** 
     * @param filename
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
        try{
		CyclingPortal portal = null;
		//Creating FileOutputStream object.
		FileInputStream fis = 
            new FileInputStream(filename+".ser");

            //Creating ObjectOutputStream object.
            ObjectInputStream ois = new ObjectInputStream(fis);

            //write object.
            portal = (CyclingPortal) ois.readObject();

            //close streams.
            ois.close();
            fis.close();
            this.Races = portal.Races;
            this.Stages = portal.Stages;
            this.Segments = portal.Segments;
            this.Teams = portal.Teams;
            this.Riders = portal.Riders;

            this.Races.get(0).setNumberOfRaces(Races.size());
            this.Stages.get(0).setNumberOfStages(Stages.size());
            this.Segments.get(0).setNumberOfSegments(Segments.size());
            this.Teams.get(0).setNumberOfTeams(Teams.size());
            this.Riders.get(0).setNumberOfRiders(Riders.size());
            	         
	      } catch(IOException e) {
	            throw new IOException("There was a problem reading that file"); // Throws new IOException "There was a problem reading that file");
	      } catch(ClassNotFoundException e) {
                throw new ClassNotFoundException("Class files were not found"); // Throws new ClassNotFoundException "Class files were not found"
          }        
	}
        
    

    
    /** 
     * @param name
     * @throws NameNotRecognisedException
     */
    @Override
    public void removeRaceByName(String name) throws NameNotRecognisedException{
        boolean found = false; // Declares a variable found and sets it equal to false
        int raceId = 0;
        for (Race race : Races){  // Loops through all the races in the race array list
            if (race.getName()==name){ // Checks if the race name for that race is equal to the given race name
                found = true; // Sets found equal to true  
                raceId = race.getId();
                for (Stage stage : Stages){ // Loops through all the stages in the stage array list
                    if (race.getId() == stage.getRaceID()){ // Checks if the Id of the race is equal to the race id in the stage
                        for (int i=0;i<Segments.size();i++){ // Loops through the loop i number of times where i is equal to the length of the segment array list
                            if(Segments.get(i).getStageId() == stage.getStageId()){ // checks if the segment at that index in the array list has the same stage id as the current stage id
                                Segments.remove(i); // Removes the segment at that index of the segment array list
                            }
                        }
                    }
                }
                for (int i=0;i<Stages.size();i++){ // Loops through the loop i number of times where i is equal to the legnth of the stages array list
                    if(Stages.get(i).getRaceID() == raceId){ // checks if the race at that index in the array list has the same race id as the current race id
                        Stages.remove(i); // Removes the stage at that index of the stage array list
                    }
                }
                break; // Breaks from the for loop
            }
        }
        if (!found){ // Checks if the found variable is equal to false
            throw new NameNotRecognisedException("That Race name is not recognised"); // Throws a new NameNotRecognisedExcception with message: 'That Race name is not recognised'
        }
        for (int i=0; i<Races.size(); i++){ // Loops through the following code i number of times, where i is the length of the races array list
            if (Races.get(i).getName() == name){ // Checks if the race id for that race is equal to the given race id
                Races.remove(i); // Removes that race from the system
                break; // Breaks from the for loop
            }
        }
    }

    
    /** 
     * @param raceId
     * @return int[]
     * @throws IDNotRecognisedException
     */
    @Override
    public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
        assert raceId > 0 : "Race ID must be greater than 0"; // Throws an assertion exception if the raceId is less than 0
        boolean found = false; // Sets variable found to false
        for (Race race: Races) { // For each race in Races
            if (race.getId() == raceId) { // If current race's id is equal too the given race id
                found = true; // Sets found variable to true
                break;
            }
        }

        if (!found) { // If not found
            throw new IDNotRecognisedException("Race ID not recognized"); // Throws a new IDNotRecognisedException with message "Race ID not recognized"
        }
        
        LinkedHashMap<Integer, LocalTime> totalTimes = new LinkedHashMap<Integer,LocalTime>(); // Create new totalTimes LinkedHashMap
        LinkedHashMap<Integer, LocalTime> tempTotalTimes = new LinkedHashMap<Integer, LocalTime>(); // Create new tempTotalTimes LinkedHashMap
        for (Rider rider : Riders){ // For each rider in Riders ArrayList
            totalTimes.put(rider.getId(), LocalTime.of(0,0)); // Give each rider an initial time of 00:00
            for (Stage stage : Stages){ // For each stage in Stages ArrayList
                if (stage.getRaceID() == raceId){ // If current stage race id matches the given race id
                    LocalTime adjElapsedTime = getRiderAdjustedElapsedTimeInStage(stage.getStageId(), rider.getId()); // LocalTime variable 'adjElapsedTime' is set to the elapsed time returned for the current rider in the current stage
                    totalTimes.put(rider.getId(), totalTimes.get(rider.getId()).plusNanos(adjElapsedTime.toNanoOfDay())); // Replaces the current value in totalTimes for the current rider with the current time value + the current stage's returned elapsed time
                }
            }            
        }
        List list = new LinkedList(totalTimes.entrySet()); // Creates a new LinkedList with the values of totalTimes' entrySet
        //Custom Comparator  
        Collections.sort(list, new Comparator() {  
        public int compare(Object o1, Object o2) {  
            return ((Comparable) ((LocalTime)((Map.Entry) (o1)).getValue())).compareTo(((LocalTime)((Map.Entry) (o2)).getValue()));  // Return the result of comparing the 2 given times
            }  
        });

        //copying the sorted list in HashMap to preserve the iteration order  
        HashMap sortedHashMap = new LinkedHashMap(); // Create new HashMap
        for (Iterator it = list.iterator(); it.hasNext();){  // For each iterator in the list
            Map.Entry entry = (Map.Entry) it.next();  // Sets the current iteration from the list to a Map.Entry variable
            sortedHashMap.put(entry.getKey(), entry.getValue()); // Puts the current key and value from the linked list into the HashMap
        }   
        Map<Integer, LocalTime> map = sortedHashMap; // Copy sortedHashMap to a new Map called map
        Set set2 = map.entrySet();  // Puts the value of map's entry set into a Set
        Iterator iterator2 = set2.iterator();  // Create new iterator from the set
        while(iterator2.hasNext()){  // While the iterator has a next value
        Map.Entry me2 = (Map.Entry)iterator2.next(); // Store the next value in iterator in me2
        tempTotalTimes.put((Integer)me2.getKey(), (LocalTime)me2.getValue()); // Puts the key and value from me2 into tempTotalTimes LinkedHashMap        
        }
        totalTimes = tempTotalTimes; // Sets totalTimes to store tempTotalTimes LinkedHashMap
        int[] toReturn = new int[totalTimes.size()]; // Create new integer array with size being the size of totalTimes
        int count = 0; // Sets integer count to 0
        for (int key : totalTimes.keySet()){ // For each key in totalTimes' key set
            toReturn[count] = key; // Puts key into the array at position count
            count++; // Increment count by 1
        }
        return toReturn; // Return the list
    }

    
    /** 
     * @param raceId
     * @return LocalTime[]
     * @throws IDNotRecognisedException
     */
    @Override
    public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
        assert raceId > 0 : "Race ID must be greater than 0"; // Throws an assertion exception if the raceId is less than 0
        boolean found = false; // Sets variable found to false
        for (Race race: Races) { // For each race in Races
            if (race.getId() == raceId) { // If current race's id is equal too the given race id
                found = true; // Sets found variable to true
                break;
            }
        }

        if (!found) { // If not found
            throw new IDNotRecognisedException("Race ID not recognized"); // Throws a new IDNotRecognisedException with message "Race ID not recognized"
        }
        
        LinkedHashMap<Integer, LocalTime> totalTimes = new LinkedHashMap<Integer,LocalTime>(); // Create new totalTimes LinkedHashMap
        LinkedHashMap<Integer, LocalTime> tempTotalTimes = new LinkedHashMap<Integer, LocalTime>(); // Create new tempTotalTimes LinkedHashMap
        for (Rider rider : Riders){ // For each rider in Riders ArrayList
            totalTimes.put(rider.getId(), LocalTime.of(0,0)); // Give each rider an initial time of 00:00
            for (Stage stage : Stages){ // For each stage in Stages ArrayList
                if (stage.getRaceID() == raceId){ // If current stage race id matches the given race id
                    LocalTime adjElapsedTime = getRiderAdjustedElapsedTimeInStage(stage.getStageId(), rider.getId()); // LocalTime variable 'adjElapsedTime' is set to the elapsed time returned for the current rider in the current stage
                    totalTimes.put(rider.getId(), totalTimes.get(rider.getId()).plusNanos(adjElapsedTime.toNanoOfDay())); // Replaces the current value in totalTimes for the the current rider with the current time value + the current stage's returned elapsed time
                }
            }            
        }
        List list = new LinkedList(totalTimes.entrySet());  // Creates a new LinkedList with the values of totalTimes' entrySet
        //Custom Comparator  
        Collections.sort(list, new Comparator() {  
        public int compare(Object o1, Object o2) {  
            return ((Comparable) ((LocalTime)((Map.Entry) (o1)).getValue())).compareTo(((LocalTime)((Map.Entry) (o2)).getValue()));  // Return the result of comparing the 2 given times
            }  
        });

        //copying the sorted list in HashMap to preserve the iteration order  
        HashMap sortedHashMap = new LinkedHashMap(); // Create new HashMap
        for (Iterator it = list.iterator(); it.hasNext();){  // For each iterator in the list
            Map.Entry entry = (Map.Entry) it.next();  // Sets the current iteration from the list to a Map.Entry variable
            sortedHashMap.put(entry.getKey(), entry.getValue()); // Puts the current key and value from the linked list into the HashMap
        }   
        Map<Integer, LocalTime> map = sortedHashMap; // Copy sortedHashMap to a new Map called map
        Set set2 = map.entrySet();  // Puts the value of map's entry set into a Set
        Iterator iterator2 = set2.iterator();  // Create new iterator from the set
        while(iterator2.hasNext()){  // While the iterator has a next value
        Map.Entry me2 = (Map.Entry)iterator2.next(); // Store the next value in iterator in me2
        tempTotalTimes.put((Integer)me2.getKey(), (LocalTime)me2.getValue()); // Puts the key and value from me2 into tempTotalTimes LinkedHashMap        
        }
        totalTimes = tempTotalTimes; // Sets totalTimes to store tempTotalTimes LinkedHashMap
        LocalTime[] toReturn = new LocalTime[totalTimes.size()]; // Create new LocalTime array with size being the size of totalTimes
        int count = 0; // Sets integer count to 0
        for (LocalTime value : totalTimes.values()){ // For each value in totalTimes' set of values
            toReturn[count] = value; // Puts value into the array at position count
            count++; // Increment count by 1
        }
        return toReturn; // Return the list
    }

    
    /** 
     * @param raceId
     * @return int[]
     * @throws IDNotRecognisedException
     */
    @Override
    public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
        assert raceId > 0 : "Race ID must be greater than 0"; // Throws an assertion exception if the raceId is less than 0
        boolean found = false; // Sets variable found to false
        LinkedHashMap<Integer,Integer> adjPointsMap = new LinkedHashMap<Integer,Integer>(); // Creates new LinkedHashMap called adjPointsMap
        for (Race race: Races) { // For each race in Races
            if (race.getId() == raceId) { // If current race's id is equal too the given race id
                found = true; // Sets found variable to true
                break;
            }
        }
        if (!found) { // If not found
            throw new IDNotRecognisedException("Race ID not recognized"); // Throws a new IDNotRecognisedException with message "Race ID not recognized"
        }
        for (Rider rider : Riders){ // For each rider in Riders ArrayList
            adjPointsMap.put(rider.getId(), 0); // Gives each rider an initial value of 0 points
        }
        for (Stage stage : Stages){ // For each stage in Stages ArrayList
            if (stage.getRaceID() == raceId) { // If current stage race id matches the given race id
                int[] riderIds = getRidersRankInStage(stage.getStageId()); // Creates new integer array storing the ordered rider ids for the stage
                int[] riderPoints = getRidersPointsInStage(stage.getStageId()); // Creates new integer array storing the points in the stage, ordered corresponding to the riderIds array
                for (int i=0; i<riderIds.length; i++){ // For i in range 0 - riderIds' length
                    adjPointsMap.put(riderIds[i], adjPointsMap.get(riderIds[i]) + riderPoints[i]); // Replaces the current points in totalTimes for the current rider with their current points + the current stage's returned points
                }
            }
        }
        int[] GCRiderIds = getRidersGeneralClassificationRank(raceId); // Creates new integer array storing the returned rider ids returned for the current race's general classification
        int[] points = new int[GCRiderIds.length]; // Creates a new integer array with a size of the length of GCRiderIds (Number of riders in the race)
        for (int i=0;i<GCRiderIds.length; i++) { // For i in range 0 - length of GCRiderIds
            points[i] = adjPointsMap.get(GCRiderIds[i]); // Stores in points at position i, the value in adjPointsMap corresponding to the key which is the value of GCRiderIds at position i
        }
        return points; // Returns the points array
    }

    
    /** 
     * @param raceId
     * @return int[]
     * @throws IDNotRecognisedException
     */
    @Override
    public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException{
        assert raceId > 0 : "Race ID must be greater than 0"; // Throws an assertion exception if the raceId is less than 0
        boolean found = false; // Sets variable found to false
        LinkedHashMap<Integer,Integer> adjPointsMap = new LinkedHashMap<Integer,Integer>(); // Creates new LinkedHashMap called adjPointsMap
        for (Race race: Races) { // For each race in Races
            if (race.getId() == raceId) { // If current race's id is equal too the given race id
                found = true; // Sets found variable to true
                break;
            }
        }
        if (!found) { // If not found
            throw new IDNotRecognisedException("Race ID not recognized"); // Throws a new IDNotRecognisedException with message "Race ID not recognized"
        }
        for (Rider rider : Riders){ // For each rider in Riders ArrayList
            adjPointsMap.put(rider.getId(), 0); // Gives each rider an initial value of 0 points
        }
        for (Stage stage : Stages){ // For each stage in Stages ArrayList
            if (stage.getRaceID() == raceId) { // If current stage race id matches the given race id
                int[] riderIds = getRidersRankInStage(stage.getStageId()); // Creates new integer array storing the ordered rider ids for the stage
                int[] riderPoints = getRidersMountainPointsInStage(stage.getStageId()); // Creates new integer array storing the mountain points in the stage, ordered corresponding to the riderIds array
                for (int i=0; i<riderIds.length; i++){ // For i in range 0 - riderIds' length
                    adjPointsMap.put(riderIds[i], adjPointsMap.get(riderIds[i]) + riderPoints[i]); // Replaces the current points in totalTimes for the current rider with their current mountain points + the current stage's returned mountain points
                }
            }
        }
        int[] GCRiderIds = getRidersGeneralClassificationRank(raceId); // Creates new integer array storing the returned rider ids returned for the current race's general classification
        int[] points = new int[GCRiderIds.length]; // Creates a new integer array with a size of the length of GCRiderIds (Number of riders in the race)
        for (int i=0;i<GCRiderIds.length; i++) { // For i in range 0 - length of GCRiderIds
            points[i] = adjPointsMap.get(GCRiderIds[i]); // Stores in points at position i, the value in adjPointsMap corresponding to the key which is the value of GCRiderIds at position i
        }
        return points; // Returns the points array
    }

    
    /** 
     * @param raceId
     * @return int[]
     * @throws IDNotRecognisedException
     */
    @Override
    public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
        assert raceId > 0 : "Race ID must be greater than 0"; // Throws an assertion exception if the raceId is less than 0
        boolean found = false; // Sets variable found to false
        LinkedHashMap<Integer,Integer> pointsMap = new LinkedHashMap<Integer,Integer>(); // Creates new LinkedHashMap called pointsMap
        for (Race race: Races) { // For each race in Races
            if (race.getId() == raceId) { // If current race's id is equal too the given race id
                found = true; // Sets found variable to true
                break;
            }
        }
        if (!found) { // If not found
            throw new IDNotRecognisedException("Race ID not recognized"); // Throws a new IDNotRecognisedException with message "Race ID not recognized"
        }
        for (Rider rider : Riders){ // For each rider in Riders ArrayList
            pointsMap.put(rider.getId(), 0); // Gives each rider an initial value of 0 points
        }
        for (Stage stage : Stages){ // For each stage in Stages ArrayList
            if (stage.getRaceID() == raceId) { // If current stage race id matches the given race id
                int[] riderIds = getRidersRankInStage(stage.getStageId()); // Creates new integer array storing the ordered rider ids for the stage
                int[] riderPoints = getRidersPointsInStage(stage.getStageId()); // Creates new integer array storing the points in the stage, ordered corresponding to the riderIds array
                for (int i=0; i<riderIds.length; i++){ // For i in range 0 - riderIds' length
                    pointsMap.put(riderIds[i], pointsMap.get(riderIds[i]) + riderPoints[i]); // Replaces the current points in totalTimes for the current rider with their current points + the current stage's returned points
                }
            }
        }
        List<Map.Entry<Integer, Integer> > list = new ArrayList<Map.Entry<Integer, Integer> >(pointsMap.entrySet()); // Creates new List containing Map.Entry elements which contain pointsMap's entry set
 
        // Using collections class sort method
        // and inside which we are using
        // custom comparator to compare value of map
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer> >() {
                // Comparing two entries by value
                public int compare(
                    Map.Entry<Integer, Integer> entry1,
                    Map.Entry<Integer, Integer> entry2)
                { 
                    // Substracting the entries
                    return entry2.getValue() - entry1.getValue(); // Return the result of the difference between the values of entry1 and entry 2
                }
            });
        int[] pointsList = new int[pointsMap.size()]; // Create new integer array with the size being the size of pointsMap
        int count = 0; // Set an integer count to 0
        for (Map.Entry<Integer, Integer> l : list) { // For each Map entry in list
            pointsList[count] = l.getValue(); // Stores the current entry's value in pointsList at position count
            count++; // Increment count by 1
        }            
        return pointsList; // Returns the pointsList array
    }

    
    /** 
     * @param raceId
     * @return int[]
     * @throws IDNotRecognisedException
     */
    @Override
    public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
        assert raceId > 0 : "Race ID must be greater than 0"; // Throws an assertion exception if the raceId is less than 0
        boolean found = false; // Sets variable found to false
        LinkedHashMap<Integer,Integer> pointsMap = new LinkedHashMap<Integer,Integer>(); // Creates new LinkedHashMap called pointsMap
        for (Race race: Races) { // For each race in Races
            if (race.getId() == raceId) { // If current race's id is equal too the given race id
                found = true; // Sets found variable to true
                break;
            }
        }
        if (!found) { // If not found
            throw new IDNotRecognisedException("Race ID not recognized"); // Throws a new IDNotRecognisedException with message "Race ID not recognized"
        }
        for (Rider rider : Riders){ // For each rider in Riders ArrayList
            pointsMap.put(rider.getId(), 0); // Gives each rider an initial value of 0 points
        }
        for (Stage stage : Stages){ // For each stage in Stages ArrayList
            if (stage.getRaceID() == raceId) { // If current stage race id matches the given race id
                int[] riderIds = getRidersRankInStage(stage.getStageId()); // Creates new integer array storing the ordered rider ids for the stage
                int[] riderPoints = getRidersMountainPointsInStage(stage.getStageId()); // Creates new integer array storing the points in the stage, ordered corresponding to the riderIds array
                for (int i=0; i<riderIds.length; i++){ // For i in range 0 - riderIds' length
                    pointsMap.put(riderIds[i], pointsMap.get(riderIds[i]) + riderPoints[i]); // Replaces the current points in totalTimes for the current rider with their current points + the current stage's returned points
                }
            }
        }
        List<Map.Entry<Integer, Integer> > list = new ArrayList<Map.Entry<Integer, Integer> >(pointsMap.entrySet()); // Creates new List containing Map.Entry elements which contain pointsMap's entry set
 
        // Using collections class sort method
        // and inside which we are using
        // custom comparator to compare value of map
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer> >() {
                // Comparing two entries by value
                public int compare(
                    Map.Entry<Integer, Integer> entry1,
                    Map.Entry<Integer, Integer> entry2)
                { 
                    // Substracting the entries
                    return entry2.getValue() - entry1.getValue(); // Return the result of the difference between the values of entry1 and entry 2
                }
            });
        int[] pointsList = new int[pointsMap.size()]; // Create new integer array with the size being the size of pointsMap
        int count = 0; // Set an integer count to 0
        for (Map.Entry<Integer, Integer> l : list) { // For each Map entry in list
            pointsList[count] = l.getValue(); // Stores the current entry's value in pointsList at position count
            count++; // Increment count by 1
        }            
        return pointsList; // Returns the pointsList array
    }
    
}
