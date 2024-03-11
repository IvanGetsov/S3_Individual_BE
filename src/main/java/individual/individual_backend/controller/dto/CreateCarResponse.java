package individual.individual_backend.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCarResponse {
    private Integer carId;
}
