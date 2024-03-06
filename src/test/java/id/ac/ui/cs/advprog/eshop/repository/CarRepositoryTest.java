package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarRepositoryTest {

    @InjectMocks
    CarRepository carRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Car car = new Car();
        car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Toyota");
        car.setCarColor("Blue");
        car.setCarQuantity(100);
        carRepository.create(car);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car.getCarId(), savedCar.getCarId());
        assertEquals(car.getCarName(), savedCar.getCarName());
        assertEquals(car.getCarColor(), savedCar.getCarColor());
        assertEquals(car.getCarQuantity(), savedCar.getCarQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneCar() {
        Car car1 = createAndAddCar("Toyota", "Red", 20);
        Car car2 = createAndAddCar("Honda", "Black", 15);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car1.getCarId(), savedCar.getCarId());
        savedCar = carIterator.next();
        assertEquals(car2.getCarId(), savedCar.getCarId());
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testUpdateExistingCar() {
        Car originalCar = createAndAddCar("Toyota", "Yellow", 25);
        Car updatedCar = new Car();
        updatedCar.setCarId(originalCar.getCarId());
        updatedCar.setCarName("Updated Toyota");
        updatedCar.setCarColor("Updated Yellow");
        updatedCar.setCarQuantity(30);

        Car result = carRepository.update(originalCar.getCarId(), updatedCar);

        assertNotNull(result);
        assertEquals(originalCar.getCarId(), result.getCarId());
        assertEquals("Updated Toyota", result.getCarName());
        assertEquals("Updated Yellow", result.getCarColor());
        assertEquals(30, result.getCarQuantity());
    }

    @Test
    void testUpdateNonExistingCar() {
        Car updatedCar = new Car();
        updatedCar.setCarId(UUID.randomUUID().toString());
        updatedCar.setCarName("Updated Honda");
        updatedCar.setCarColor("Updated Blue");
        updatedCar.setCarQuantity(40);

        Car result = carRepository.update(updatedCar.getCarId(), updatedCar);

        assertNull(result);
    }

    @Test
    void testDeleteExistingCar() {
        Car carToDelete = createAndAddCar("Nissan", "Silver", 18);
        carRepository.delete(carToDelete.getCarId());
        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testDeleteNonExistingCar() {
        carRepository.delete(UUID.randomUUID().toString());
        assertTrue(true);
    }

    @Test
    void testFindByIdExistingCar() {
        Car car = createAndAddCar("Toyota", "Green", 25);
        Car foundCar = carRepository.findById(car.getCarId());
        assertNotNull(foundCar);
        assertEquals(car, foundCar);
    }

    @Test
    void testFindByIdNonExistingCar() {
        Car foundCar = carRepository.findById(UUID.randomUUID().toString());
        assertNull(foundCar);
    }


    private Car createAndAddCar(String name, String color, int quantity) {
        Car car = new Car();
        car.setCarName(name);
        car.setCarColor(color);
        car.setCarQuantity(quantity);
        carRepository.create(car);
        return car;
    }
}
