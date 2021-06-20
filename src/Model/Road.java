package Model;

import java.awt.*;
import java.util.ArrayList;

public class Road {

    public enum Orientation {
        HORIZONTAL, VERTICAL
    }

    private Orientation orientation;
    private String id;
    private int speedLimit;
    private int length;
    private int width;
    private int[] startLocation;
    private int[] endLocation;
    private ArrayList<Vehicle> vehiclesOnRoad = new ArrayList<>();
    private ArrayList<TrafficLight> lightsOnRoad = new ArrayList<>();
    private ArrayList<Road> connectedRoads = new ArrayList<>();


    public Road(String id, int speedLimit, int length, int[] startLocation, Orientation orientation) {
        this.id = "road_" + id;
        this.speedLimit = speedLimit;
        this.length = length;
        width = 8;
        this.orientation = orientation;
        this.startLocation = startLocation;
        setEndLocation();
    }

    public void draw(Graphics g, int scale) {
        if (orientation == Orientation.HORIZONTAL) {
            int[] startLocation = this.startLocation;
            int x = startLocation[0] * scale;
            int y = startLocation[1] * scale;
            int width = length * scale;
            int height = this.width * scale;
            g.setColor(Color.darkGray);
            g.fillRect(x, y, width, height);
            //Center Lines
            g.setColor(Color.white);
            g.fillRect(x, y + (height / 2) - scale / 6, width, scale / 6);
            g.fillRect(x, y + (height / 2) + scale / 6, width, scale / 6);
        } else if (orientation == Orientation.VERTICAL) {
            int[] startLocation = this.startLocation;
            int x = startLocation[0] * scale;
            int y = startLocation[1] * scale;
            int width = this.width * scale;
            int height = length * scale;
            g.setColor(Color.darkGray);
            g.fillRect(x, y, width, height);
//            //Center Lines
            g.setColor(Color.white);
            g.fillRect(x + (width / 2) - scale / 6, y, scale / 6, height);
            g.fillRect(x + (width / 2) + scale / 6, y, scale / 6, height);
        }
    }


    String getId() {
        return id;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public int getLength() {
        return length;
    }

    int getWidth() {
        return width;
    }

    private void setEndLocation() {
        if (orientation == Orientation.HORIZONTAL) {
            this.endLocation = new int[]{this.length + this.startLocation[0], this.startLocation[1]}; //only works for horizontal roads;
        } else if (orientation == Orientation.VERTICAL) {
            this.endLocation = new int[]{this.startLocation[1], this.length + this.startLocation[0]};
        }
    }

    public int[] getStartLocation() {
        return startLocation;
    }

    public int[] getEndLocation() {
        return endLocation;
    }

    public ArrayList<Vehicle> getVehiclesOnRoad() {
        return vehiclesOnRoad;
    }

    public ArrayList<TrafficLight> getLightsOnRoad() {
        return lightsOnRoad;
    }

    public ArrayList<Road> getConnectedRoads() {
        return connectedRoads;
    }

    Orientation getOrientation() {
        return orientation;
    }

}
