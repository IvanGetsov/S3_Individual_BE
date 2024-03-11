package individual.individual_backend.controller.dto;

import individual.individual_backend.domain.Car;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetCarsResponse {
    private List<Car> cars;
}
