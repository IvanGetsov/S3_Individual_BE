package individual.individual_backend.controller.dto;

import individual.individual_backend.domain.Chat;
import individual.individual_backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMessageRequest {
    private Chat chat;
    private User sender;
    private User recipient;
    private String text;
}
