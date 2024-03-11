package individual.individual_backend.business.impl;

import individual.individual_backend.business.exception.InvalidCredentialsException;
import individual.individual_backend.configuration.security.token.AccessTokenEncoder;
import individual.individual_backend.controller.dto.LoginRequest;
import individual.individual_backend.controller.dto.LoginResponse;
import individual.individual_backend.domain.Role;
import individual.individual_backend.persistence.UserRepository;
import individual.individual_backend.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

 class LoginUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @InjectMocks
    private LoginUseCaseImpl loginUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_withValidCredentials_shouldReturnAccessToken() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("valid@email.com", "password123");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setEmail("valid@email.com");
        userEntity.setPassword(passwordEncoder.encode("password123"));
        userEntity.setRole(Role.CAR_OWNER);

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())).thenReturn(true);

        // Act
        LoginResponse loginResponse = loginUseCase.login(loginRequest);

        // Assert
        assertNotNull(loginResponse);
        verify(userRepository, times(1)).findByEmail(loginRequest.getEmail());
        verify(passwordEncoder, times(1)).matches(loginRequest.getPassword(), userEntity.getPassword());
    }

    @Test
    void login_withInvalidCredentials_shouldThrowInvalidCredentialsException() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("invalid@email.com", "wrongpassword");

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(loginRequest));
        verify(userRepository, times(1)).findByEmail(loginRequest.getEmail());
        verify(passwordEncoder, never()).matches(any(), any());
    }

    @Test
    void login_withInvalidPassword_shouldThrowInvalidCredentialsException() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("valid@email.com", "wrongpassword");
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("valid@email.com");
        userEntity.setPassword(passwordEncoder.encode("correctpassword"));

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(loginRequest));
        verify(userRepository, times(1)).findByEmail(loginRequest.getEmail());
        verify(passwordEncoder, times(1)).matches(loginRequest.getPassword(), userEntity.getPassword());
    }
}
