package individual.individual_backend.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "advert")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "car_owner_id")
    private UserEntity user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private CarEntity car;

    @Column(name = "available_from", columnDefinition = "DATE")
    private LocalDate availableFrom;

    @Column(name = "maximum_Available_Days")
    private Integer maximumAvailableDays;

    @Column(name = "price_Per_Day")
    private Double pricePerDay;

    @Lob @Basic(fetch=LAZY)
    @Column(name = "picture", columnDefinition = "LONGBLOB")
    private String picture;
}
