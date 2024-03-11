package individual.individual_backend.business.impl;

import individual.individual_backend.business.UserManager;
import individual.individual_backend.business.converters.UsersConvertor;
import individual.individual_backend.domain.Role;
import individual.individual_backend.domain.User;
import individual.individual_backend.persistence.UserRepository;

import individual.individual_backend.persistence.entity.UserEntity;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserManagerImplTest {
    private UserRepository userRepositoryMock;
    private UserManager userManager;
    private PasswordEncoder passwordEncoder;
    @BeforeEach
    void setUp() {
        userRepositoryMock = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userManager = new UserManagerImpl(userRepositoryMock, passwordEncoder);
    }
    /**
     * @verifies return a unique id when user is created
     * @see UserManagerImpl#createUser(User, String) 
     */
    @Test
    void createUser_shouldReturnAUniqueIdWhenUserIsCreated() throws Exception {
        // Arrange
        User user = User.builder()
                .name("Ivan")
                .age(5)
                .role(Role.CAR_OWNER)
                .id(1)
                .build();
        String password = "test";

        UserEntity userEntity = UsersConvertor.toEntity(user);

        when(userRepositoryMock.save(userEntity)).thenReturn(userEntity);

        // Act
        Integer actualResult = userManager.createUser(UsersConvertor.toDomain(userEntity), password);

        Integer expectedResult = 1;

        // Assert
        assertEquals(expectedResult, actualResult);
        verify(userRepositoryMock).save(userEntity);
    }


    /**
     * @verifies return an empty arrayList when no users are present
     * @see UserManagerImpl#getUsers()
     */
    @Test
    void getUsers_shouldReturnAnEmptyArrayListWhenNoUsersArePresent() throws Exception {
        // Arrange
        when(userRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        // Act
        List<User> actualUsers = userManager.getUsers();
        // Assert
        assertEquals(0, actualUsers.size());
        verify(userRepositoryMock).findAll();
    }

    /**
     * @verifies return an arrayList with cars when users are present
     * @see UserManagerImpl#getUsers()
     */
    @Test
    void getUsers_shouldReturnAnArrayListWithCarsWhenUsersArePresent() throws Exception {
        // Arrange
        User user = User.builder().name("Ivan").age(5).role(Role.CAR_OWNER).build();
        User user2 = User.builder().name("Emily").age(6).role(Role.CAR_RENTER).build();
        List<UserEntity> userEntities = Arrays.asList(
                UsersConvertor.toEntity(user),
                UsersConvertor.toEntity(user2)
        );

        when(userRepositoryMock.findAll()).thenReturn(userEntities);

        // Act
        List<User> actualUsers = userManager.getUsers();

        // Assert
        assertEquals(2, actualUsers.size());
        verify(userRepositoryMock).findAll();
    }


    /**
     * @verifies remove the user with the given ID from the repository
     * @see UserManagerImpl#deleteUser(int)
     */
    @Test
    void deleteUser_shouldRemoveTheUserWithTheGivenIDFromTheRepository() throws Exception {
        // Arrange
        int userIdToDelete = 1;
        doNothing().when(userRepositoryMock).deleteById(userIdToDelete);
        // Act
        userManager.deleteUser(userIdToDelete);
        // Assert
        verify(userRepositoryMock).deleteById(userIdToDelete);
    }

    /**
     * @verifies update an existing user's details if it exists in the repository
     * @see UserManagerImpl#updateUser(User)
     */
    @Test
    void updateUser_shouldUpdateAnExistingUsersDetailsIfItExistsInTheRepository() throws Exception {
        // Arrange
        User userToUpdate = User.builder()
                .id(1)
                .name("Name")
                .age(20)
                .role(Role.CAR_RENTER)
                .build();

        User updatedUser = User.builder()
                .id(1)
                .name("Updated Name")
                .age(25)
                .role(Role.CAR_RENTER)
                .build();

        UserEntity userEntityToUpdate = UsersConvertor.toEntity(userToUpdate);

        Optional<UserEntity> optionalUserEntity = Optional.of(userEntityToUpdate);

        when(userRepositoryMock.findById(userToUpdate.getId())).thenReturn(optionalUserEntity);

        // Act
        userManager.updateUser(updatedUser);

        // Assert
        verify(userRepositoryMock).save(UsersConvertor.toEntity(updatedUser));
    }

    @Test
    void getUser_shouldReturnNullWhenUserWithGivenIDNotFound() {
        // Arrange
        int nonExistingUserId = 100;

        when(userRepositoryMock.findById(nonExistingUserId)).thenReturn(Optional.empty());

        // Act
        User actualUser = userManager.getUser(nonExistingUserId);

        // Assert
        assertNull(actualUser);
        verify(userRepositoryMock).findById(nonExistingUserId);
    }

    @Test
    void existsByEmail_shouldReturnTrueIfUserEmailExists() {
        // Arrange
        String existingEmail = "existing@email.com";

        when(userRepositoryMock.existsByEmail(existingEmail)).thenReturn(true);

        // Act
        boolean result = userManager.existsByEmail(existingEmail);

        // Assert
        assertTrue(result);
        verify(userRepositoryMock).existsByEmail(existingEmail);
    }

    @Test
    void existsByEmail_shouldReturnFalseIfUserEmailDoesNotExist() {
        // Arrange
        String nonExistingEmail = "nonexisting@email.com";

        when(userRepositoryMock.existsByEmail(nonExistingEmail)).thenReturn(false);

        // Act
        boolean result = userManager.existsByEmail(nonExistingEmail);

        // Assert
        assertFalse(result);
        verify(userRepositoryMock).existsByEmail(nonExistingEmail);
    }

    @Test
    void updateEmail_shouldReturnFalseIfNewEmailAlreadyExists() {
        // Arrange
        int userId = 1;
        String existingEmail = "existing@email.com";
        String newEmail = "new@email.com";

        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);
        existingUser.setEmail(existingEmail);

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepositoryMock.existsByEmail(newEmail)).thenReturn(true);

        // Act
        boolean result = userManager.updateEmail(userId, newEmail);

        // Assert
        assertFalse(result);
        verify(userRepositoryMock).findById(userId);
        verify(userRepositoryMock).existsByEmail(newEmail);
    }

    @Test
    void updateEmail_shouldUpdateEmailIfNewEmailDoesNotExist() {
        // Arrange
        int userId = 1;
        String existingEmail = "existing@email.com";
        String newEmail = "new@email.com";

        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);
        existingUser.setEmail(existingEmail);

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepositoryMock.existsByEmail(newEmail)).thenReturn(false);

        // Act
        boolean result = userManager.updateEmail(userId, newEmail);

        // Assert
        assertTrue(result);
        assertEquals(newEmail, existingUser.getEmail());
        verify(userRepositoryMock).findById(userId);
        verify(userRepositoryMock).existsByEmail(newEmail);
        verify(userRepositoryMock).save(existingUser);
    }

    @Test
    void updatePassword_shouldReturnFalseIfCurrentPasswordIncorrect() {
        // Arrange
        int userId = 1;
        String currentPassword = "incorrectPassword";
        String newPassword = "newPassword";

        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);
        existingUser.setPassword("encodedCorrectPassword");

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(currentPassword, existingUser.getPassword())).thenReturn(false);

        // Act
        boolean result = userManager.updatePassword(userId, currentPassword, newPassword);

        // Assert
        assertFalse(result);
        verify(userRepositoryMock).findById(userId);
        verify(passwordEncoder).matches(currentPassword, existingUser.getPassword());
        verify(userRepositoryMock, never()).save(existingUser);
    }

    @Test
    void updatePassword_shouldUpdatePasswordIfCurrentPasswordCorrect() {
        // Arrange
        int userId = 1;
        String currentPassword = "correctPassword";
        String newPassword = "newPassword";

        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);
        existingUser.setPassword("encodedCorrectPassword");

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(existingUser));

        // Add a check for null password
        when(passwordEncoder.matches(eq(currentPassword), nullable(String.class)))
                .thenReturn(true);

        // Act
        boolean result = userManager.updatePassword(userId, currentPassword, newPassword);

        // Assert
        assertTrue(result);
        verify(userRepositoryMock).findById(userId);
        verify(passwordEncoder).matches(eq(currentPassword), nullable(String.class));
        verify(passwordEncoder).encode(newPassword);
        verify(userRepositoryMock).save(existingUser);
    }


    @Test
    void saveProfilePicture_shouldSaveProfilePictureForUser() {
        // Arrange
        int userId = 1;
        String profilePicture = "profilePictureBytes";

        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(existingUser));

        // Act
        userManager.saveProfilePicture(userId, profilePicture);

        // Assert
        assertEquals(profilePicture, existingUser.getPicture());
        verify(userRepositoryMock).findById(userId);
        verify(userRepositoryMock).saveAndFlush(existingUser);
    }


}
