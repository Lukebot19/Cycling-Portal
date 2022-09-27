package cycling;

import java.io.Serializable;

public class Rider implements Serializable {
    private final int teamId;
    private final String name;
    private final int yearOfBirth;
    private final int id;
    private static int numberOfRiders = 0;
    

    public Rider(int teamId, String name, int yearOfBirth) {
        this.teamId = teamId;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.id = ++numberOfRiders;
    }

    
    
    /** 
     * @return int
     */
    public int getTeamId() { 
        return teamId; 
    }
    
    /** 
     * @return String
     */
    public String getName() { 
        return name; 
    }
    
    /** 
     * @return int
     */
    public int getYearOfBirth() { 
        return yearOfBirth; 
    }
    
    /** 
     * @return int
     */
    public int getId() { 
        return id; 
    }

   
    public void clearNumberOfRiders() { 
        numberOfRiders = 0; 
    }
    
    /** 
     * @param numberOfRiders
     */
    public void setNumberOfRiders(int numberOfRiders) {
        Rider.numberOfRiders = numberOfRiders;
    }
    

}
