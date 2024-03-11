package individual.individual_backend.business.impl;


import individual.individual_backend.business.CarManager;
import individual.individual_backend.business.converters.CarsConverter;
import individual.individual_backend.domain.Car;
import individual.individual_backend.persistence.CarRepository;
import individual.individual_backend.persistence.entity.CarEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class CarManagerImplTest {
    private CarRepository carRepositoryMock;
    private CarManager carManager;

    @BeforeEach
    void setUp() {
        carRepositoryMock = mock(CarRepository.class);
        carManager = new CarManagerImpl(carRepositoryMock);
    }
    /**
     * @verifies return a unique id when car is created
     * @see CarManagerImpl#createCar(Car)
     */
    @Test
    void createCar_shouldReturnAUniqueIdWhenCarIsCreated() {
    // Arrange
    Car car = Car.builder()
            .id(1)
            .yearOfConstruction(2000)
            .kilometers(30000)
            .model("bmw")
            .colour("green")
            .build();

        CarEntity carEntity = CarsConverter.toEntity(car);
        when(carRepositoryMock.save(carEntity)).thenReturn(carEntity);
    // Act
        Integer actualResult = carManager.createCar(CarsConverter.toDomain(carEntity));

        Integer expectedResult = 1;
    // Assert
        assertEquals(expectedResult, actualResult);
        verify(carRepositoryMock).save(carEntity);
    }

    /**
     * @verifies return an empty arrayList when no cars are present
     * @see CarManagerImpl#getCars()
     */
    @Test
    void getCars_shouldReturnAnEmptyArrayListWhenNoCarsArePresent() throws Exception {
        // Arrange
        when(carRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        // Act
        List<Car> actualCars = carManager.getCars();
        // Assert
        assertEquals(0, actualCars.size());
        verify(carRepositoryMock).findAll();
    }

    /**
     * @verifies return an arrayList with cars when cars are present
     * @see CarManagerImpl#getCars()
     */
    @Test
    void getCars_shouldReturnAnArrayListWithCarsWhenCarsArePresent() throws Exception {
        // Arrange
        Car car = Car.builder()
                .id(1)
                .yearOfConstruction(2000)
                .kilometers(30000)
                .model("bmw")
                .colour("green")
                .build();
        Car car2 = Car.builder()
                .id(2)
                .yearOfConstruction(2005)
                .kilometers(20000)
                .model("audi")
                .colour("black")
                .build();
        List<CarEntity> carsEntities = Arrays.asList(
                CarsConverter.toEntity(car),
                CarsConverter.toEntity(car2)
        );
        when(carRepositoryMock.findAll()).thenReturn(carsEntities);
        // Act
        List<Car> actualCars = carManager.getCars();
        // Assert
        assertEquals(2, actualCars.size());
        verify(carRepositoryMock).findAll();
    }


    /**
     * @verifies update an existing car's details if it exists in the repository
     * @see CarManagerImpl#updateCar(Car)
     */
    @Test
    void updateCar_shouldUpdateAnExistingCarsDetailsIfItExistsInTheRepository() throws Exception {
        // Arrange
        Car carToUpdate = Car.builder()
                .id(1)
                .yearOfConstruction(2000)
                .kilometers(30000)
                .model("bmw")
                .colour("green")
                .build();
        Car updatedCar = Car.builder()
                .id(1)
                .yearOfConstruction(2005)
                .kilometers(20000)
                .model("audi")
                .colour("black")
                .build();

        CarEntity carEntityToUpdate = CarsConverter.toEntity(carToUpdate);

        Optional<CarEntity> optionalCarEntity = Optional.of(carEntityToUpdate);

        when(carRepositoryMock.findById(carToUpdate.getId())).thenReturn(optionalCarEntity);

        // Act
        carManager.updateCar(updatedCar);

        // Assert
        verify(carRepositoryMock).save(CarsConverter.toEntity(updatedCar));
    }

    @Test
    void getDistinctBrands_shouldReturnListOfDistinctCarBrands() {
        // Arrange
        List<String> distinctBrands = Arrays.asList("BMW", "Audi", "Toyota");
        when(carRepositoryMock.findDistinctBrands()).thenReturn(distinctBrands);

        // Act
        List<String> actualDistinctBrands = carManager.getDistinctBrands();

        // Assert
        assertEquals(distinctBrands.size(), actualDistinctBrands.size());
        assertTrue(actualDistinctBrands.containsAll(distinctBrands));
        verify(carRepositoryMock).findDistinctBrands();
    }




    @Test
    void getCar_shouldReturnCarWhenCarIdIsNotZero() {
        // Arrange
        int carId = 1;
        CarEntity carEntity = new CarEntity();

        when(carRepositoryMock.findById(carId)).thenReturn(Optional.of(carEntity));

        // Act
        Car actualCar = carManager.getCar(carId);

        // Assert
        assertEquals(CarsConverter.toDomain(carEntity), actualCar);
        verify(carRepositoryMock).findById(carId);
    }
}
