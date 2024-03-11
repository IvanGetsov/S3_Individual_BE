package individual.individual_backend.controller.converters;

import individual.individual_backend.controller.dto.CreateMessageRequest;
import individual.individual_backend.domain.Message;

public class MessageConverter {
    private MessageConverter() {
    }

    public static Message convertCreateRequestToDomain(CreateMessageRequest request) {
        return Message.builder()
                .sender(request.getSender())
                .recipient(request.getRecipient())
                .text(request.getText())
                .chat(request.getChat())
                .build();
    }
}
