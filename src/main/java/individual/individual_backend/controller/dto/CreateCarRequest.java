package individual.individual_backend.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarRequest {
    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    @Min(1900)
    private Integer yearOfConstruction;
    @NotBlank
    private String colour;
    @Min(1)
    private Integer kilometers;
}
