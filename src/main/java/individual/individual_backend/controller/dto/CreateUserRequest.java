package individual.individual_backend.controller.dto;

import individual.individual_backend.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateUserRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @Email
    private String email;
    @Min(18)
    private Integer age;
    @NotNull
    private Role role;
    private String picture;
}
