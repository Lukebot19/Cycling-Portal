package cycling;

import java.io.Serializable;

public class Segment implements Comparable<Segment>, Serializable {
    private int stageId;
    private double location;
    private SegmentType type;
    private double averageGradient;
    private double length;
    private int id;
    private static int numberOfSegments = 0;

    public Segment(int stageId, double location, SegmentType type, double averageGradient, double length) {
        this.stageId = stageId;
        this.location = location;
        this.type = type;
        this.averageGradient = averageGradient;
        this.length = length;
        id = ++numberOfSegments;
    }

    public Segment(int stageId, double location, SegmentType type) {
        this.stageId = stageId;
        this.location = location;
        this.type = type;
        id = ++numberOfSegments;
    }


    
    
    /** 
     * @return int
     */
    public int getStageId() { 
        return stageId; 
    }
    
    /** 
     * @return double
     */
    public double getLocation() { 
        return location; 
    }
    
    /** 
     * @return SegmentType
     */
    public SegmentType getType() { 
        return type;
    }
    
    /** 
     * @return double
     */
    public double getAverageGradient() { 
        return averageGradient; 
    }
    
    /** 
     * @return double
     */
    public double getLength() { 
        return length; 
    }
    
    /** 
     * @return int
     */
    public int getSegmentId() { 
        return id; 
    }
    
    /** 
     * @return int
     */
    public static int getNumberOfSegments() { 
        return numberOfSegments; 
    }
    

    
    public void clearNumberOfSegments() { 
        numberOfSegments = 0; 
    }
    
    /** 
     * @param numberOfSegments
     */
    public void setNumberOfSegments( int numberOfSegments ) { 
        Segment.numberOfSegments = numberOfSegments; 
    } 

    
    /** 
     * @param s
     * @return int
     */
    @Override
	public int compareTo(Segment s) {
		return Double.compare(this.getLocation(), s.getLocation());
	}
}
