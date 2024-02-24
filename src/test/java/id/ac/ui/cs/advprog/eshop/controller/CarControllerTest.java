package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CarControllerTest {

    @InjectMocks
    private CarController carController;

    @Mock
    private CarService carService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCarPage() {
        String viewName = carController.createCarPage(model);
        assertEquals("CreateCar", viewName);
    }

    @Test
    void createCarPost() {
        Car car = new Car();
        String viewName = carController.createCarPost(car, model);
        assertEquals("redirect:listCar", viewName);
    }

    @Test
    void carListPage() {
        List<Car> mockCars = new ArrayList<>();
        when(carService.findAll()).thenReturn(mockCars);

        String viewName = carController.carListPage(model);

        assertEquals("CarList", viewName);
        verify(model, times(1)).addAttribute("cars", mockCars);
    }

    @Test
    void editCarPage() {
        String carId = "testCarId";
        Car mockCar = new Car();
        when(carService.findById(carId)).thenReturn(mockCar);

        String viewName = carController.editCarPage(carId, model);

        assertEquals("EditCar", viewName);
        verify(model, times(1)).addAttribute("car", mockCar);
    }

    @Test
    void editCarPost() {
        Car updatedCar = new Car();
        String viewName = carController.editCarPost(updatedCar, model);

        assertEquals("redirect:listCar", viewName);
        verify(carService, times(1)).update(updatedCar.getCarId(), updatedCar);
    }

    @Test
    void deleteCar() {
        String carId = "testCarId";
        String viewName = carController.deleteCar(carId);

        assertEquals("redirect:listCar", viewName);
        verify(carService, times(1)).deleteCarById(carId);
    }
}