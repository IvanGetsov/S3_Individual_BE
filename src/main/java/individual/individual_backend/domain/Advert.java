package individual.individual_backend.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Advert {
    private Integer id;
    private User carOwner;
    private Car car;
    private LocalDate availableFrom;
    private Integer maximumAvailableDays;
    private Double pricePerDay;
    private String picture;
}