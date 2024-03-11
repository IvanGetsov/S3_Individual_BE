package individual.individual_backend.business.impl;

import individual.individual_backend.business.CarManager;
import individual.individual_backend.business.converters.CarsConverter;
import individual.individual_backend.domain.Car;
import individual.individual_backend.persistence.CarRepository;
import individual.individual_backend.persistence.entity.CarEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CarManagerImpl implements CarManager {
    private CarRepository repository;

    /**
     * @return an integer when a car is created
     * @should return a unique id when car is created
     */


    @Override
    public Integer createCar(Car car) {
        CarEntity carEntity = CarsConverter.toEntity(car);
        CarEntity savedCar = repository.save(carEntity);
        return savedCar.getId();
    }

    /**
     * @return an arrayList when asked for cars
     * @should return an empty arrayList when no cars are present
     * @should return an arrayList with cars when cars are present
     */
    @Override
    public List<Car> getCars() {
        return repository.findAll().stream().map(CarsConverter::toDomain).toList();
    }

    @Override
    public Car getCar(Integer carId) {
        Optional<CarEntity> carEntityOptional = repository.findById(carId);

        if (carEntityOptional.isPresent()) {
            CarEntity carEntity = carEntityOptional.get();
            return CarsConverter.toDomain(carEntity);
        } else {
            return null;
        }
    }

    /**
     * @param carId The ID of the car to delete.
     * @should remove the car with the given ID from the repository
     */
    @Override
    public void deleteCar(int carId) {
        repository.deleteById(carId);
    }

    /**
     *
     * @param car The car object containing the updated information.
     *@should update an existing car's details if it exists in the repository
     */
    @Override
    public void updateCar(Car car) {
        Optional<CarEntity> carEntityOptional = repository.findById(car.getId());

        if (carEntityOptional.isPresent()) {
            CarEntity carToUpdate = carEntityOptional.get();
            updateCarFields(carToUpdate, car);
            repository.save(carToUpdate);
        }
    }

    private void updateCarFields(CarEntity existingCar, Car newCar) {
        existingCar.setBrand(newCar.getBrand());
        existingCar.setModel(newCar.getModel());
        existingCar.setYearOfConstruction(newCar.getYearOfConstruction());
        existingCar.setColour(newCar.getColour());
        existingCar.setKilometers(newCar.getKilometers());
    }

    @Override
    public List<String> getDistinctBrands() {return repository.findDistinctBrands();
    }


}
