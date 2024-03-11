package individual.individual_backend.persistence.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "car")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "year_Of_Construction")
    private Integer yearOfConstruction;

    @Column(name = "colour")
    private String colour;

    @Column(name = "kilometers")
    private Integer kilometers;
}
