package individual.individual_backend.business;

import individual.individual_backend.domain.Chat;

import java.util.List;

public interface ChatManager {
    Integer createChat(Chat chat);
    Chat getChat(Integer chatId);
    Boolean isChatPresent(Integer advertId, Integer senderId, Integer recipientId);
    Chat getChatByAdvertAndUsers(Integer advertId, Integer senderId, Integer recipientId);
    List<Chat> getUsersChats(Integer userId);
}
