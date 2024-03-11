package individual.individual_backend.controller.dto;

import individual.individual_backend.domain.Car;
import individual.individual_backend.domain.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdvertRequest {
    private User carOwner;
    private Car car;
    @NotNull
    private LocalDate availableFrom;
    @NotNull
    private Integer maximumAvailableDays;
    @Min(1)
    private Double pricePerDay;
    private String picture;
}
