package individual.individual_backend.business.converters;

import individual.individual_backend.domain.Chat;
import individual.individual_backend.domain.Message;
import individual.individual_backend.persistence.entity.ChatEntity;
import individual.individual_backend.persistence.entity.MessageEntity;

public class MessagesConverter {
    private MessagesConverter(){

    }

    public static MessageEntity toEntity(Message message, Integer chatId) {
        ChatEntity chat = ChatEntity.builder()
                .id(chatId)
                .build();

        return MessageEntity.builder()
                .id(message.getId())
                .sender(UsersConvertor.toEntity(message.getSender()))
                .recipient(UsersConvertor.toEntity(message.getRecipient()))
                .text(message.getText())
                .chat(chat)
                .time(message.getTime())
                .build();
    }

    public static Message toDomain(MessageEntity messageEntity, Integer chatId) {
        Chat chat = Chat.builder()
                .id(chatId)
                .build();

        Message message = new Message();
        message.setId(messageEntity.getId());
        message.setSender(UsersConvertor.toDomain(messageEntity.getSender()));
        message.setRecipient(UsersConvertor.toDomain(messageEntity.getRecipient()));
        message.setText(messageEntity.getText());
        message.setChat(chat);
        message.setTime(messageEntity.getTime());
        return message;
    }
}
