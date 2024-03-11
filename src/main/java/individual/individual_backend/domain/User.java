package individual.individual_backend.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    private String email;
    private String password;
    private Integer id;
    private String name;
    private Integer age;
    private Role role;
    private String picture;
}
