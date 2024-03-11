package individual.individual_backend.business.converters;

import individual.individual_backend.domain.Car;
import individual.individual_backend.persistence.entity.CarEntity;

import java.util.Objects;

public class CarsConverter {

    private CarsConverter(){

    }
    public static CarEntity toEntity(Car car) {
        if (Objects.isNull(car)){
            return CarEntity.builder().build();
        }

        CarEntity carEntity = new CarEntity();
        carEntity.setId(car.getId());
        carEntity.setBrand(car.getBrand());
        carEntity.setModel(car.getModel());
        carEntity.setColour(car.getColour());
        carEntity.setKilometers(car.getKilometers());
        carEntity.setYearOfConstruction(car.getYearOfConstruction());
        return carEntity;
    }

    public static Car toDomain(CarEntity carEntity) {
        Car car = new Car();
        car.setId(carEntity.getId());
        car.setBrand(carEntity.getBrand());
        car.setModel(carEntity.getModel());
        car.setColour(carEntity.getColour());
        car.setKilometers(carEntity.getKilometers());
        car.setYearOfConstruction(carEntity.getYearOfConstruction());
        return car;
    }


    }



