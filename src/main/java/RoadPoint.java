import java.awt.*;
import java.util.ArrayList;

public class RoadPoint {

    private ArrayList<Point> arrayDots;
    private String connectionType;
    private String lampDistance;
    private String lampCount;
    private String streetName;
    private String category;

    public RoadPoint(String connectionType, ArrayList<Point> dots, String lampDistance, String lampCount, String category, String streetName){
        this.connectionType = connectionType;
        this.arrayDots = dots;
        this.lampDistance = lampDistance;
        this.lampCount = lampCount;
        this.category = category;
        this.streetName= streetName;
    }

    public ArrayList<Point> getArrayDots() {
        return arrayDots;
    }

    public void setDots(ArrayList<Point> dots) {
        this.arrayDots = dots;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getLampDistance() {
        return lampDistance;
    }

    public void setLampDistance(String lampDistance) {
        this.lampDistance = lampDistance;
    }

    public String getLampCount() {
        return lampCount;
    }

    public void setLampCount(String lampCount) {
        this.lampCount = lampCount;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
