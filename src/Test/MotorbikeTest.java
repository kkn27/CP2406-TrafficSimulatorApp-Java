package Test;

import Model.Road;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MotorbikeTest {
    Model.Motorbike bike = new Model.Motorbike("0", new Model.Road("0", 1, 5, new int[]{0, 0}, Road.Orientation.VERTICAL));

    @Test
    void getLength() {
        assertEquals(2.0, bike.getLength());
    }

    @Test
    void getId() {
        assertEquals("bike_0", bike.getId());
    }

    @Test
    void testInheritance() {
        assertEquals(0, bike.getSpeed());
        assertEquals(-2, bike.getPosition());
    }
}