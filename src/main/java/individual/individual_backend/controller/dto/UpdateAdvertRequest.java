package individual.individual_backend.controller.dto;

import individual.individual_backend.domain.Car;
import individual.individual_backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdvertRequest {
    private Integer id;
    private User carOwner;
    private Car car;
    private LocalDate availableFrom;
    private Integer maximumAvailableDays;
    private Double pricePerDay;
    private String picture;
}
