package individual.individual_backend.business.impl;

import individual.individual_backend.business.MessageManager;
import individual.individual_backend.business.converters.MessagesConverter;
import individual.individual_backend.domain.Message;
import individual.individual_backend.persistence.MessageRepository;
import individual.individual_backend.persistence.entity.MessageEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class MessageManagerImpl implements MessageManager {
    private MessageRepository messageRepository;

    @Override
    public Integer createMessage(Message message) {
        message.setTime(LocalDateTime.now());
        MessageEntity messageEntity = MessagesConverter.toEntity(message, message.getChat().getId());
        System.out.println(messageEntity.getRecipient().getId());
        MessageEntity savedMessage = messageRepository.save(messageEntity);

        return savedMessage.getId();
    }


}
