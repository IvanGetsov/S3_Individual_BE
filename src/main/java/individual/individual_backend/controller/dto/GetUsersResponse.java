package individual.individual_backend.controller.dto;

import individual.individual_backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GetUsersResponse {
    private List<User> users;
}
