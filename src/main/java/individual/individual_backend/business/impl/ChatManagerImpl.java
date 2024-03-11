package individual.individual_backend.business.impl;

import individual.individual_backend.business.ChatManager;
import individual.individual_backend.business.converters.ChatsConverter;
import individual.individual_backend.domain.Chat;
import individual.individual_backend.persistence.ChatRepository;
import individual.individual_backend.persistence.entity.ChatEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatManagerImpl implements ChatManager {
    private ChatRepository chatRepository;
    @Override
    public Integer createChat(Chat chat) {
        ChatEntity chatEntity = ChatsConverter.toEntity(chat);
        ChatEntity savedChat = chatRepository.save(chatEntity);
        return savedChat.getId();
    }

    @Override
    public Chat getChat(Integer chatId) {
        Optional<ChatEntity> chatEntityOptional = chatRepository.findById(chatId);

        if(chatEntityOptional.isPresent()){
            ChatEntity chatEntity = chatEntityOptional.get();
            return ChatsConverter.toDomain(chatEntity);
        }
        else{
            return null;
        }
    }

    @Override
    public Boolean isChatPresent(Integer advertId, Integer senderId, Integer recipientId) {
        return chatRepository.doesChatExist(advertId, senderId, recipientId);
    }

    @Override
    public Chat getChatByAdvertAndUsers(Integer advertId, Integer senderId, Integer recipientId) {
        boolean chatExists = chatRepository.doesChatExist(advertId, senderId, recipientId);

        if (chatExists) {
            Optional<ChatEntity> chatEntityOptional = chatRepository.findChat(advertId, senderId, recipientId);
            return chatEntityOptional.map(ChatsConverter::toDomain).orElse(null);
        } else {
            return null;
        }
    }

    @Override
    public List<Chat> getUsersChats(Integer userId) {
        List<ChatEntity> chatEntities = chatRepository.findChatsByUserId(userId);
        return chatEntities.stream().map(ChatsConverter::toDomain).toList();
    }
}

