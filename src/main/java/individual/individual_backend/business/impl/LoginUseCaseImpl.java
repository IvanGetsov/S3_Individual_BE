package individual.individual_backend.business.impl;

import individual.individual_backend.business.LoginUseCase;
import individual.individual_backend.business.exception.InvalidCredentialsException;
import individual.individual_backend.configuration.PasswordEncoderConfig;
import individual.individual_backend.configuration.security.token.AccessTokenEncoder;
import individual.individual_backend.configuration.security.token.impl.AccessTokenImpl;
import individual.individual_backend.controller.dto.LoginRequest;
import individual.individual_backend.controller.dto.LoginResponse;
import individual.individual_backend.domain.Role;
import individual.individual_backend.persistence.UserRepository;
import individual.individual_backend.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<UserEntity> user = repository.findByEmail(loginRequest.getEmail());
        if (user.isEmpty()) {

            throw new InvalidCredentialsException();
        }

        if (!matchesPassword(loginRequest.getPassword(), user.get().getPassword())) {

            throw new InvalidCredentialsException();
        }

        String accessToken = generateAccessToken(user.get());
        return LoginResponse.builder().accessToken(accessToken).build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(UserEntity user) {
        Integer userId = user.getId();
        String role = user.getRole().toString();


        return accessTokenEncoder.encode(
                new AccessTokenImpl(user.getEmail(), userId, role));
    }
}
