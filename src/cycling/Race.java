package cycling;

import java.io.Serializable;

public class Race implements Serializable {
    private final String name;
    private final String description;
    private final int id;
    private static int numberOfRaces = 0;
    

    public Race(String name, String description) {
        this.name = name;
        this.description = description;
        id = ++numberOfRaces;
    
    }
    
    /** 
     * @return int
     */
    public int getId() { 
        return id; 
    }

    
    /** 
     * @return String
     */
    public String getName() { 
        return name; 
    }

    
    /** 
     * @return Integer
     */
    public Integer getNumberOfRaces() { 
        return Race.numberOfRaces; 
    }

    
    /** 
     * @return String
     */
    public String getDescription(){ 
        return description; 
    }

    public void clearNumberOfRaces() {
        numberOfRaces=0;
    }

    
    /** 
     * @param numberOfRaces
     */
    public void setNumberOfRaces(int numberOfRaces) {
        Race.numberOfRaces = numberOfRaces;
    }

}
