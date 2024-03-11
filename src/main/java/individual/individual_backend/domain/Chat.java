package individual.individual_backend.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Chat {
    private Integer id;
    private Advert advert;
    private List<Message> messages;
}
