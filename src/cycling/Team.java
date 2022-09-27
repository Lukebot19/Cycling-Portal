package cycling;

import java.io.Serializable;

public class Team implements Serializable{
    private String name;
    private String description;
    private int teamID;
    private static int numberOfTeams = 0;

    public Team(String name, String description) {
        this.name = name;
        this.description = description;
        this.teamID = ++numberOfTeams; 
    }
    
    
    /** 
     * @return String
     */
    //Getters
    public String getName() { 
        return name;
    }
    
    /** 
     * @return String
     */
    public String getDescription() { 
        return description; 
    }
    
    /** 
     * @return int
     */
    public int getTeamId() { 
        return teamID; 
    }

    public void clearNumberOfTeams() {
        numberOfTeams=0;
    }

    
    /** 
     * @param numberOfTeams
     */
    //setters
    public void setNumberOfTeams( int numberOfTeams ) { 
        Team.numberOfTeams = numberOfTeams; 
    } 

}
