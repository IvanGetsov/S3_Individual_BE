package individual.individual_backend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarRequest {
    private Integer carId;
    private String brand;
    private String model;
    private Integer yearOfConstruction;
    private String colour;
    private Integer kilometers;
}
