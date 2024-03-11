package individual.individual_backend.controller;

import individual.individual_backend.business.MessageManager;
import individual.individual_backend.business.UserManager;
import individual.individual_backend.controller.converters.MessageConverter;
import individual.individual_backend.controller.dto.CreateMessageRequest;
import individual.individual_backend.controller.dto.CreateMessageResponse;
import individual.individual_backend.controller.dto.SendMessageRequest;
import individual.individual_backend.domain.Message;
import individual.individual_backend.domain.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessagesContoller {
    private final MessageManager messageManager;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserManager userManager;
    @PostMapping
    public ResponseEntity<CreateMessageResponse> createMessage(@RequestBody @Valid CreateMessageRequest request) {
        Message message = MessageConverter.convertCreateRequestToDomain(request);
        Integer messageId = messageManager.createMessage(message);
        CreateMessageResponse response = CreateMessageResponse.builder()
                .id(messageId)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @MessageMapping("/send")
    public ResponseEntity<Void> sendMessageToUsers(@RequestBody SendMessageRequest request) {
        final User sender = userManager.getUser(request.getSender().getId());
        request.setSender(sender);

        String destination = "/user/" + request.getChatId() + "/queue/inboxmessages";

        messagingTemplate.convertAndSend(destination, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
