package individual.individual_backend.controller;

import individual.individual_backend.business.CarManager;
import individual.individual_backend.controller.converters.CarConverter;
import individual.individual_backend.controller.dto.CreateCarRequest;
import individual.individual_backend.controller.dto.CreateCarResponse;
import individual.individual_backend.controller.dto.GetCarsResponse;
import individual.individual_backend.controller.dto.UpdateCarRequest;
import individual.individual_backend.domain.Car;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@AllArgsConstructor
public class CarsController {
    private final CarManager carManager;

    @PostMapping
    public ResponseEntity<CreateCarResponse> createCar(@RequestBody @Valid CreateCarRequest request){
        Car car = CarConverter.convertCreateRequestToDomain(request);
        Integer carId = carManager.createCar(car);
        CreateCarResponse response = CreateCarResponse.builder()
                .carId(carId)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<GetCarsResponse> getCars(){
        GetCarsResponse response = GetCarsResponse.builder()
                .cars(carManager.getCars())
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{carId}")
        public ResponseEntity<Void> deleteCar(@PathVariable int carId){
        carManager.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCar(@PathVariable("id") Integer id,
                                          @RequestBody @Valid UpdateCarRequest request) {
        Car existingCar = carManager.getCar(id);

        if (existingCar == null) {
            return ResponseEntity.notFound().build();
        }
        existingCar.setModel(request.getModel());
        existingCar.setYearOfConstruction(request.getYearOfConstruction());
        existingCar.setColour(request.getColour());
        existingCar.setKilometers(request.getKilometers());
        carManager.updateCar(existingCar);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/distinct-brands")
    public ResponseEntity<List<String>> getDistinctBrands() {
        List<String> distinctBrands = carManager.getDistinctBrands();
        return ResponseEntity.ok(distinctBrands);
    }

}
