package Model;

public class Motorbike extends Vehicle {

    public Motorbike(String id, Road currentRoad) {
        super(currentRoad);
        this.id = ("bike_" + id);
        setLength(super.getLength() / 2);
        breadth = super.getBreadth() / 2;
        position = -length;
    }

}
