package individual.individual_backend.controller.converters;

import individual.individual_backend.controller.dto.CreateChatRequest;
import individual.individual_backend.domain.Chat;

public class ChatConverter {
    private ChatConverter() {
    }

    public static Chat convertCreateRequestToDomain(CreateChatRequest request) {
        return Chat.builder()
                .advert(request.getAdvert())
                .build();
    }
}
