package individual.individual_backend.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "chat")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "advert_id")
    private AdvertEntity advertId;

    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    @OrderBy("time ASC")
    private List<MessageEntity> messages;
}
