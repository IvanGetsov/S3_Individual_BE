package individual.individual_backend.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Message {
    private Integer id;
    private Chat chat;
    private User sender;
    private User recipient;
    private String text;
    private LocalDateTime time;
}
