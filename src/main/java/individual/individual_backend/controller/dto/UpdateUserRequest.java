package individual.individual_backend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UpdateUserRequest {
    private Integer id;
    private String password;
    private String name;
    private String email;
    private Integer age;
    private String picture;
}
