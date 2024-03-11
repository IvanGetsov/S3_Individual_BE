package individual.individual_backend.business.converters;

import individual.individual_backend.domain.Chat;
import individual.individual_backend.domain.Message;
import individual.individual_backend.persistence.entity.ChatEntity;
import individual.individual_backend.persistence.entity.MessageEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChatsConverter {

    private ChatsConverter(){

    }

    public static ChatEntity toEntity(Chat chat) {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(chat.getId());
        chatEntity.setAdvertId(AdvertsConverter.toEntity(chat.getAdvert()));

        List<MessageEntity> messages = new ArrayList<>();
        if(chat.getMessages() != null){
            chatEntity.setMessages(chat.getMessages().stream().map(message -> MessagesConverter.toEntity(message, chatEntity.getId())).toList());
        }
        else{
            chatEntity.setMessages(messages);
        }
        return chatEntity;
    }

    public static Chat toDomain(ChatEntity chatEntity) {
        Chat chat = new Chat();
        chat.setId(chatEntity.getId());
        chat.setAdvert(AdvertsConverter.toDomain(chatEntity.getAdvertId()));
        chat.setMessages(chatEntity.getMessages().stream().map(message -> MessagesConverter.toDomain(message, chat.getId())).toList());
        return chat;
    }
}
