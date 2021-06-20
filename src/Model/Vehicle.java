package Model;

import java.awt.*;
import java.util.Random;

public abstract class Vehicle {

    private static final int STOPPED = 0;
    private static final int START_POSITION = 0;
    int length; // number of segments occupied
    int breadth;
    String id; // unique identifier
    private int speed; //segments moved per turn
    private Road currentRoad; // current Model.Road object
    int position; // position on current road
    private Color colour;
    private Random random = new Random();


    public Vehicle(Road currentRoad) {
        id = "000";
        length = 4;
        breadth = 2;
        speed = 0;
        this.currentRoad = currentRoad;
        currentRoad.getVehiclesOnRoad().add(this); //add this vehicle to the road its on.
        colour = randomColour();
    }

    public Vehicle() {
        id = "000";
        length = 0;
        breadth = 0;
        speed = 0;
        position = 0;
    }

    public void move() {
        Random random = new Random();
        int nextPosition = position + length + speed;
        //vehicle in front check:
        for (Vehicle nextVehicle : currentRoad.getVehiclesOnRoad()) {
            if (nextVehicle.position > position && nextVehicle.position <= nextPosition + 4) {
                speed = STOPPED;
                break;
            } else {
                speed = currentRoad.getSpeedLimit();
            }
        }
        //red light check:
        if (speed == STOPPED) { //intentionally left empty
        } else {
            if (!currentRoad.getLightsOnRoad().isEmpty() && nextPosition + 1 >= currentRoad.getLightsOnRoad().get(0).getPosition() && this.currentRoad.getLightsOnRoad().get(0).getState().equals("red")) {
                speed = STOPPED;
            } else {
                speed = currentRoad.getSpeedLimit();
                if (currentRoad.getLength() <= nextPosition && !currentRoad.getConnectedRoads().isEmpty()) {
                    currentRoad.getVehiclesOnRoad().remove(this);
                    int nextRoadIndex = random.nextInt(2);
                    currentRoad = currentRoad.getConnectedRoads().get(nextRoadIndex);
                    currentRoad.getVehiclesOnRoad().add(this);
                    position = START_POSITION;
                } else if (currentRoad.getLength() >= nextPosition) {
                    position = (position + speed);
                } else {
                    speed = STOPPED;
                }
            }
        }
    }

    public void draw(Graphics g, int scale) {
        int xValue = 0;
        int yValue = 1;
        if (currentRoad.getOrientation() == Road.Orientation.HORIZONTAL) {
            int[] startLocation = getCurrentRoad().getStartLocation();
            int width = getLength() * scale;
            int height = getBreadth() * scale;
            int x = (getPosition() + startLocation[xValue]) * scale;
            int y = (startLocation[yValue] * scale) + scale;
            g.setColor(colour);
            g.fillRect(x, y, width, height);
        } else if (currentRoad.getOrientation() == Road.Orientation.VERTICAL) {
            int[] startLocation = getCurrentRoad().getStartLocation();
            int width = getBreadth() * scale;
            int height = getLength() * scale;
            int x = (startLocation[xValue] * scale) + ((currentRoad.getWidth() * scale) - (width + scale));
            int y = (getPosition() + startLocation[yValue]) * scale;
            g.setColor(colour);
            g.fillRect(x, y, width, height);
        }
    }

    private Color randomColour() {
        int r = random.nextInt(245 + 1) + 10;
        int g = random.nextInt(245 + 1) + 10;
        int b = random.nextInt(245 + 1) + 10;
        return new Color(r, g, b);
    }


    public void printStatus() {
        System.out.printf("%s going:%dm/s on %s at position:%s%n", this.getId(), this.getSpeed(), this.getCurrentRoad().
                getId(), this.getPosition());
    }

    public int getLength() {
        return length;
    }

    void setLength(int length) {
        this.length = length;
    }

    public int getBreadth() {
        return breadth;
    }

    public int getSpeed() {
        return speed;
    }

    public int getPosition() {
        return position;
    }

    public Road getCurrentRoad() {
        return currentRoad;
    }

    public String getId() {
        return id;
    }


}

