package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private Model model;

    @Mock
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCar() {
        Car car = new Car();
        car.setCarId("testCarId");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        when(carRepository.create(car)).thenReturn(car);
        Car createdCar = carService.create(car);

        assertNotNull(createdCar);
        assertEquals("testCarId", createdCar.getCarId());
        assertEquals("Toyota", createdCar.getCarName());
        assertEquals("Red", createdCar.getCarColor());
        assertEquals(5, createdCar.getCarQuantity());

        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        when(carRepository.findAll()).thenReturn(Collections.emptyIterator());
        carService.findAll();

        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        String carId = "testCarId";
        Car existingCar = new Car();
        existingCar.setCarId(carId);
        existingCar.setCarName("Toyota");
        existingCar.setCarColor("Red");
        existingCar.setCarQuantity(5);

        when(carRepository.findById(carId)).thenReturn(existingCar);
        Car foundCar = carService.findById(carId);

        assertNotNull(foundCar);
        assertEquals(existingCar, foundCar);
        verify(carRepository, times(1)).findById(carId);
    }

    @Test
    void testUpdateCar() {
        String carId = "testCarId";
        Car existingCar = new Car();
        existingCar.setCarId(carId);
        existingCar.setCarName("Toyota");
        existingCar.setCarColor("Red");
        existingCar.setCarQuantity(5);

        when(carRepository.update(carId, existingCar)).thenReturn(existingCar);
        carService.update(carId, existingCar);

        verify(carRepository, times(1)).update(carId, existingCar);
    }

    @Test
    void testDeleteCarById() {
        String carId = "testCarId";

        carService.deleteCarById(carId);

        verify(carRepository, times(1)).delete(carId);
    }
}
