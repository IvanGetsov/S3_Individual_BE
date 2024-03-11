package individual.individual_backend.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Car {
    private Integer id;
    private String brand;
    private String model;
    private Integer yearOfConstruction;
    private String colour;
    private Integer kilometers;
}
