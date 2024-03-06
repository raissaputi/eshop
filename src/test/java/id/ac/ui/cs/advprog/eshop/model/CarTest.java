package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    Car car;

    @BeforeEach
    void setup() {
        this.car = new Car();
        this.car.setCarId("testCarId");
        this.car.setCarName("Toyota");
        this.car.setCarColor("Merah");
        this.car.setCarQuantity(50);
    }

    @Test
    void testGetCarId() {
        assertEquals("testCarId", this.car.getCarId());
    }

    @Test
    void testGetCarName() {
        assertEquals("Toyota", this.car.getCarName());
    }

    @Test
    void testGetCarColor() {
        assertEquals("Merah", this.car.getCarColor());
    }

    @Test
    void testGetCarQuantity() {
        assertEquals(50, this.car.getCarQuantity());
    }
}
