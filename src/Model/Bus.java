package Model;

public class Bus extends Vehicle {

    public Bus(String id, Road currentRoad) {
        super(currentRoad);
        this.id = ("bus_" + id);
        setLength(super.getLength() * 3);
        position = -length;
    }

}
