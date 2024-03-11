package individual.individual_backend.business;

import individual.individual_backend.controller.dto.LoginRequest;
import individual.individual_backend.controller.dto.LoginResponse;

public interface LoginUseCase {
LoginResponse login(LoginRequest loginRequest);
}
