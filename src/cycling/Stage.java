package cycling;

import java.time.LocalTime;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class Stage implements Comparable<Stage>, Serializable{
    private final int raceID;
    private final String stageName;
    private final String description;
    private final double length;
    private final LocalDateTime startTime;
    private final StageType type;
    private boolean waitingForResults = false;
    private final int stageId;
    private static int numberOfStages = 0;


    private LinkedHashMap<Integer, LocalTime[]> stageTimes = new LinkedHashMap<Integer,LocalTime[]>();
    private LinkedHashMap<Integer, LocalTime[]> tempStageTimes = new LinkedHashMap<Integer,LocalTime[]>();

    
    public Stage(int raceID, String stageName, String description, double length, LocalDateTime startTime, StageType type) {
        this.raceID = raceID;
        this.stageName = stageName;
        this.description = description;
        this.length = length;
        this.startTime = startTime;
        this.type = type; 
        stageId = ++numberOfStages; 
    }

    
    
    /** 
     * @return int
     */
    public int getRaceID(){ 
        return raceID; 
    }

    
    /** 
     * @return String
     */
    public String getStageName(){ 
        return stageName;
    }

    
    /** 
     * @return String
     */
    public String getDescription(){ 
        return description; 
    }

    
    /** 
     * @return double
     */
    public double getLength(){ 
        return length; 
    }

    
    /** 
     * @return LocalDateTime
     */
    public LocalDateTime getStartTime(){ 
        return startTime; 
    }

    
    /** 
     * @return StageType
     */
    public StageType getType(){ 
        return type; 
    }

    
    /** 
     * @return int
     */
    public int getStageId(){ 
        return stageId; 
    }

    
    /** 
     * @return boolean
     */
    public boolean getWaitingForResults() { 
        return waitingForResults; 
    }

    
    /** 
     * @param riderId
     * @return LocalTime[]
     */
    public LocalTime[] getStageTimes(int riderId){ 
        return stageTimes.get(riderId); 
    }

    
    /** 
     * @return LinkedHashMap<Integer, LocalTime[]>
     */
    public LinkedHashMap<Integer, LocalTime[]> getHashMap() { 
        return stageTimes; 
    }
    
    /** 
     * @param riderId
     * @return int
     */
    public int getRank(int riderId){
        Set<Integer> keys = stageTimes.keySet();
        List<Integer> listKeys = new ArrayList<Integer>( keys );
        return listKeys.indexOf(riderId)+1;
    }
    
    /** 
     * @param rank
     * @return int
     */
    public int getRiderIdFromRank(int rank){
        Set<Integer> keys = stageTimes.keySet();
        List<Integer> listKeys = new ArrayList<Integer>( keys );
        return (int)listKeys.get(rank-1);
    }

    
    
    /** 
     * @param riderId
     * @param timesArray
     */
    public void setStageTimes(int riderId, LocalTime[] timesArray) {
        stageTimes.put(riderId, timesArray);
    }

    
    /** 
     * @param inState
     */
    public void setWaitingForResults(boolean inState) { 
        this.waitingForResults = inState; 
    } 

    public void clearNumberOfStages() {
        numberOfStages=0;
    }
    
    /** 
     * @param riderId
     */
    public void removeRiderResults(int riderId) {
        if (stageTimes.containsKey(riderId)) {
            stageTimes.remove(riderId);
        }
    }

    
    /** 
     * @param numberOfStages
     */
    public void setNumberOfStages( int numberOfStages ) { 
        Stage.numberOfStages = numberOfStages; 
    } 

    public void sortHashMap() {

        Map<Integer, LocalTime[]> map = sortValues(stageTimes);   
        // System.out.println("After Sorting:");  
        Set set2 = map.entrySet();  
        Iterator iterator2 = set2.iterator();  
        while(iterator2.hasNext()){  
        Map.Entry me2 = (Map.Entry)iterator2.next();
        tempStageTimes.put((Integer)me2.getKey(), (LocalTime[])me2.getValue());
        // System.out.println("Rider ID:  "+me2.getKey()+"     Time:   "+Arrays.toString((LocalTime[])me2.getValue()));
        
        }
        stageTimes = tempStageTimes;
    }

    
    /** 
     * @param map
     * @return HashMap
     */
    private static HashMap sortValues(HashMap map) {   
        List list = new LinkedList(map.entrySet());  
        //Custom Comparator  
        Collections.sort(list, new Comparator() {  
        public int compare(Object o1, Object o2) {  
            int size = ((LocalTime[])((Map.Entry) (o1)).getValue()).length;
            // System.out.println(((Comparable) ((LocalTime[])((Map.Entry) (o1)).getValue())[size-1].minusNanos(((LocalTime[])((Map.Entry) (o1)).getValue())[0].toNanoOfDay())).compareTo(((LocalTime[])((Map.Entry) (o2)).getValue())[size-1].minusNanos(((LocalTime[])((Map.Entry) (o2)).getValue())[0].toNanoOfDay())));
            return ((Comparable) ((LocalTime[])((Map.Entry) (o1)).getValue())[size-1].minusNanos(((LocalTime[])((Map.Entry) (o1)).getValue())[0].toNanoOfDay())).compareTo(((LocalTime[])((Map.Entry) (o2)).getValue())[size-1].minusNanos(((LocalTime[])((Map.Entry) (o2)).getValue())[0].toNanoOfDay()));  
        }  
    });

    //copying the sorted list in HashMap to preserve the iteration order  
        HashMap sortedHashMap = new LinkedHashMap();  
        for (Iterator it = list.iterator(); it.hasNext();){  
            Map.Entry entry = (Map.Entry) it.next();  
            sortedHashMap.put(entry.getKey(), entry.getValue());  
        }   
        return sortedHashMap;  
    }  
    
    
    /** 
     * @param s
     * @return int
     */
    @Override
	public int compareTo(Stage s) {
		return this.getStartTime().compareTo(s.getStartTime());
	}

    public void clearTimes() {
        stageTimes.clear();
        tempStageTimes.clear();
    }
}
