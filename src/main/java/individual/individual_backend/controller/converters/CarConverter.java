package individual.individual_backend.controller.converters;

import individual.individual_backend.controller.dto.CreateCarRequest;
import individual.individual_backend.controller.dto.UpdateCarRequest;
import individual.individual_backend.domain.Car;

public class CarConverter {
    private CarConverter(){

    }

    public static Car convertCreateRequestToDomain(CreateCarRequest request) {
        return Car.builder()
                .brand(request.getBrand())
                .model(request.getModel())
                .yearOfConstruction(request.getYearOfConstruction())
                .colour(request.getColour())
                .kilometers(request.getKilometers())
                .build();
    }

    public static Car convertUpdateRequestToDomain(UpdateCarRequest request) {
        return Car.builder()
                .id(request.getCarId())
                .brand(request.getBrand())
                .model(request.getModel())
                .yearOfConstruction(request.getYearOfConstruction())
                .colour(request.getColour())
                .kilometers(request.getKilometers())
                .build();
    }
}
