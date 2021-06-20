package Test;

import Model.Road;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

class RoadTest {
    Model.Road road = new Model.Road("0", 1, 5, new int[]{0, 0}, Road.Orientation.HORIZONTAL);

    @Test
    void getId() {
        assertEquals(5, road.getLength());
    }

    @Test
    void getSpeedLimit() {
        assertEquals(1, road.getSpeedLimit());
    }

    @Test
    void getLength() {
        assertEquals(5, road.getLength());
    }

    @Test
    void getStartLocationTest() {
        int[] expected1 = {0, 0};
        int[] actual = this.road.getStartLocation();
        assertArrayEquals(expected1, actual);
    }

    @Test
    void getEndLocation() {
        int[] expected = {5, 0};
        assertArrayEquals(expected, road.getEndLocation());
    }

    @Test
    void getCars() {
        ArrayList<Model.Car> expected = new ArrayList<>();
        assertEquals(expected, road.getVehiclesOnRoad());
    }

    @Test
    void getLights() {
        ArrayList<Model.TrafficLight> expected = new ArrayList<>();
        assertEquals(expected, road.getLightsOnRoad());
    }

    @Test
    void getConnectedRoads() {
        ArrayList<Model.Road> expected = new ArrayList<>();
        assertEquals(expected, road.getConnectedRoads());
    }
}