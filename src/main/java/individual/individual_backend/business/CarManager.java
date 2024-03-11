package individual.individual_backend.business;


import individual.individual_backend.domain.Car;
import java.util.List;

public interface CarManager {
    Integer createCar(Car car);
    List<Car> getCars();
    Car getCar(Integer carId);
    void deleteCar(int carId);
    void updateCar(Car car);
    List<String> getDistinctBrands();
}
