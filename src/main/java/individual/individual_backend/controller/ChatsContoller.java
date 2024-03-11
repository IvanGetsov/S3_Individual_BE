package individual.individual_backend.controller;

import individual.individual_backend.business.ChatManager;
import individual.individual_backend.controller.converters.ChatConverter;
import individual.individual_backend.controller.dto.CreateChatRequest;
import individual.individual_backend.controller.dto.CreateChatResponse;
import individual.individual_backend.domain.Chat;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
@AllArgsConstructor
public class ChatsContoller {
    private final ChatManager chatManager;

    @PostMapping
    public ResponseEntity<CreateChatResponse> createChat(@RequestBody @Valid CreateChatRequest request) {
        Chat chat = ChatConverter.convertCreateRequestToDomain(request);
        Integer chatId = chatManager.createChat(chat);
        CreateChatResponse response = CreateChatResponse.builder()
                .id(chatId)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> getChat(@PathVariable Integer chatId) {
        Chat chat = chatManager.getChat(chatId);

        if (chat != null) {
            return ResponseEntity.ok(chat);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> doesChatExist(
            @RequestParam Integer advertId,
            @RequestParam Integer senderId,
            @RequestParam Integer recipientId) {

        Boolean chatExists = chatManager.isChatPresent(advertId, senderId, recipientId);

        return ResponseEntity.ok(chatExists);
    }

    @GetMapping("/getByAdvertAndUsers")
    public ResponseEntity<Chat> getChatByAdvertAndUsers(
            @RequestParam Integer advertId,
            @RequestParam Integer senderId,
            @RequestParam Integer recipientId) {

        Chat chat = chatManager.getChatByAdvertAndUsers(advertId, senderId, recipientId);

        if (chat != null) {
            return ResponseEntity.ok(chat);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Chat>> getUsersChats(@PathVariable Integer userId) {
        List<Chat> userChats = chatManager.getUsersChats(userId);

        if (!userChats.isEmpty()) {
            return ResponseEntity.ok(userChats);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
