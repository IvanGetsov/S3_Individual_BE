package individual.individual_backend.business.impl;
import individual.individual_backend.business.MessageManager;
import individual.individual_backend.business.converters.MessagesConverter;
import individual.individual_backend.domain.*;
import individual.individual_backend.persistence.MessageRepository;
import individual.individual_backend.persistence.entity.MessageEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageManagerImplTest {
    private MessageRepository messageRepositoryMock;
    private MessageManager messageManager;

    @BeforeEach
    void setUp() {
        messageRepositoryMock = mock(MessageRepository.class);
        messageManager = new MessageManagerImpl(messageRepositoryMock);
    }

    @Test
    void createMessage_shouldReturnAUniqueIdWhenMessageIsCreated() {
        List<Message> messages = new ArrayList<>();
        LocalDate advertDate = LocalDate.of(2024, 1, 10);
        Car car = Car.builder()
                .id(1)
                .brand("brand")
                .model("model")
                .yearOfConstruction(2015)
                .colour("black")
                .kilometers(10000)
                .build();


        User user = User.builder()
                .email("test@gmail.com")
                .password("123")
                .id(1)
                .name("test")
                .age(18)
                .role(Role.CAR_OWNER)
                .picture("picture")
                .build();

        Advert advert = Advert.builder()
                .id(1)
                .carOwner(user)
                .car(car)
                .availableFrom(advertDate)
                .maximumAvailableDays(10)
                .pricePerDay(250.0)
                .picture("picture")
                .build();

        Chat chat = Chat.builder()
                .id(1)
                .advert(advert)
                .messages(messages)
                .build();

        User sender = User.builder()
                .email("sender@gmail.com")
                .password("123")
                .id(2)
                .name("sender")
                .age(18)
                .role(Role.CAR_RENTER)
                .picture("picture")
                .build();

        User recipient = User.builder()
                .email("recipient@gmail.com")
                .password("123")
                .id(3)
                .name("recipient")
                .age(18)
                .role(Role.CAR_OWNER)
                .picture("picture")
                .build();

        Message message = Message.builder().id(1).chat(chat).sender(sender).recipient(recipient).text("Ola").build();

        MessageEntity messageEntity = MessagesConverter.toEntity(message, chat.getId());
        when(messageRepositoryMock.save(any(MessageEntity.class))).thenReturn(messageEntity);

        Integer actualResult = messageManager.createMessage(message);

        assertNotNull(actualResult);
        verify(messageRepositoryMock).save(any(MessageEntity.class));
    }


}
