package individual.individual_backend.controller.dto;

import individual.individual_backend.domain.Advert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateChatRequest {
    private Advert advert;
}
