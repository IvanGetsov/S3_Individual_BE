package individual.individual_backend.business.impl;
import individual.individual_backend.business.ChatManager;
import individual.individual_backend.business.converters.ChatsConverter;
import individual.individual_backend.domain.*;
import individual.individual_backend.persistence.ChatRepository;
import individual.individual_backend.persistence.entity.ChatEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatManagerImplTest {
    private ChatRepository chatRepositoryMock;
    private ChatManager chatManager;

    @BeforeEach
    void setUp() {
        chatRepositoryMock = mock(ChatRepository.class);
        chatManager = new ChatManagerImpl(chatRepositoryMock);
    }

    @Test
    void createChat_shouldReturnAUniqueIdWhenChatIsCreated() {
        // Arrange
        List<Message> messages = new ArrayList<Message>();
        LocalDate advertDate = LocalDate.of(2024, 1, 10);
        Car car = new Car(1, "brand", "model", 2015, "black", 10000);
        User user = new User("test@gmail.com", "123", 1, "test", 18, Role.CAR_OWNER, "picture");
        Advert advert = new Advert(1, user, car, advertDate, 10, 250.0, "picture");
        Chat chat = new Chat(1, advert, messages);
        ChatEntity chatEntity = ChatsConverter.toEntity(chat);

        when(chatRepositoryMock.save(chatEntity)).thenReturn(chatEntity);

        // Act
        Integer actualResult = chatManager.createChat(chat);

        // Assert
        assertNotNull(actualResult);
        verify(chatRepositoryMock).save(chatEntity);
    }

    @Test
    void getChat_shouldReturnChatWhenItExists() {
        // Arrange
        List<Message> messages = new ArrayList<Message>();
        LocalDate advertDate = LocalDate.of(2024, 1, 10);
        Car car = new Car(1, "brand", "model", 2015, "black", 10000);
        User user = new User("test@gmail.com", "123", 1, "test", 18, Role.CAR_OWNER, "picture");
        Advert advert = new Advert(1, user, car, advertDate, 10, 250.0, "picture");
        Chat chat = new Chat(1, advert, messages);
        ChatEntity chatEntity = ChatsConverter.toEntity(chat);

        when(chatRepositoryMock.findById(chat.getId())).thenReturn(Optional.of(chatEntity));

        // Act
        Chat actualChat = chatManager.getChat(chat.getId());

        // Assert
        assertNotNull(actualChat);
        verify(chatRepositoryMock).findById(chat.getId());
    }

    @Test
    void getChat_shouldReturnNullWhenChatDoesNotExist() {
        // Arrange
        Integer chatId = 1;

        when(chatRepositoryMock.findById(chatId)).thenReturn(Optional.empty());

        // Act
        Chat actualChat = chatManager.getChat(chatId);

        // Assert
        assertNull(actualChat);
        verify(chatRepositoryMock).findById(chatId);
    }

    @Test
    void isChatPresent_shouldReturnTrueIfChatExists() {
        // Arrange
        Integer advertId = 1;
        Integer senderId = 2;
        Integer recipientId = 3;

        when(chatRepositoryMock.doesChatExist(advertId, senderId, recipientId)).thenReturn(true);

        // Act
        Boolean result = chatManager.isChatPresent(advertId, senderId, recipientId);

        // Assert
        assertTrue(result);
        verify(chatRepositoryMock).doesChatExist(advertId, senderId, recipientId);
    }

    @Test
    void isChatPresent_shouldReturnFalseIfChatDoesNotExist() {
        // Arrange
        Integer advertId = 1;
        Integer senderId = 2;
        Integer recipientId = 3;

        when(chatRepositoryMock.doesChatExist(advertId, senderId, recipientId)).thenReturn(false);

        // Act
        Boolean result = chatManager.isChatPresent(advertId, senderId, recipientId);

        // Assert
        assertFalse(result);
        verify(chatRepositoryMock).doesChatExist(advertId, senderId, recipientId);
    }

    @Test
    void getChatByAdvertAndUsers_shouldReturnChatWhenItExists() {
        // Arrange
        Integer advertId = 1;
        Integer senderId = 2;
        Integer recipientId = 3;

        List<Message> messages = new ArrayList<Message>();
        LocalDate advertDate = LocalDate.of(2024, 1, 10);
        Car car = new Car(1, "brand", "model", 2015, "black", 10000);
        User user = new User("test@gmail.com", "123", 1, "test", 18, Role.CAR_OWNER, "picture");
        Advert advert = new Advert(1, user, car, advertDate, 10, 250.0, "picture");
        Chat chat = new Chat(1, advert, messages);
        ChatEntity chatEntity = ChatsConverter.toEntity(chat);

        when(chatRepositoryMock.doesChatExist(advertId, senderId, recipientId)).thenReturn(true);
        when(chatRepositoryMock.findChat(advertId, senderId, recipientId)).thenReturn(Optional.of(chatEntity));

        // Act
        Chat actualChat = chatManager.getChatByAdvertAndUsers(advertId, senderId, recipientId);

        // Assert
        assertNotNull(actualChat);
        verify(chatRepositoryMock).doesChatExist(advertId, senderId, recipientId);
        verify(chatRepositoryMock).findChat(advertId, senderId, recipientId);
    }

    @Test
    void getChatByAdvertAndUsers_shouldReturnNullWhenChatDoesNotExist() {
        // Arrange
        Integer advertId = 1;
        Integer senderId = 2;
        Integer recipientId = 3;

        when(chatRepositoryMock.doesChatExist(advertId, senderId, recipientId)).thenReturn(false);

        // Act
        Chat actualChat = chatManager.getChatByAdvertAndUsers(advertId, senderId, recipientId);

        // Assert
        assertNull(actualChat);
        verify(chatRepositoryMock).doesChatExist(advertId, senderId, recipientId);
        verify(chatRepositoryMock, never()).findChat(advertId, senderId, recipientId);
    }

    @Test
    void getUsersChats_shouldReturnListOfChats() {
        // Arrange
        Integer userId = 1;
        List<ChatEntity> chatEntities = new ArrayList<>(); // Add some chat entities

        when(chatRepositoryMock.findChatsByUserId(userId)).thenReturn(chatEntities);

        // Act
        List<Chat> actualChats = chatManager.getUsersChats(userId);

        // Assert
        assertNotNull(actualChats);
        assertEquals(chatEntities.size(), actualChats.size());
        verify(chatRepositoryMock).findChatsByUserId(userId);
    }
}

