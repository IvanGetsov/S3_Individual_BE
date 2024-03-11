package individual.individual_backend.controller.dto;

import individual.individual_backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {
    private String id;
    private Integer chatId;
    private User sender;
    private String text;
}
