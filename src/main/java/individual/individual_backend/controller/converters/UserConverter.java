package individual.individual_backend.controller.converters;

import individual.individual_backend.controller.dto.CreateUserRequest;
import individual.individual_backend.controller.dto.UpdateUserRequest;
import individual.individual_backend.domain.User;

public class UserConverter {
private UserConverter(){

}
    public static User convertCreateRequestToDomainUser(CreateUserRequest request){
        return User.builder()
                .age(request.getAge())
                .password(request.getPassword())
                .name(request.getName())
                .role(request.getRole())
                .email(request.getEmail())
                .picture(request.getPicture())
                .build();
    }

    public static User convertUpdateRequestToDomain(UpdateUserRequest request) {
        return User.builder()
                .id(request.getId())
                .password(request.getPassword())
                .age(request.getAge())
                .name(request.getName())
                .email(request.getEmail())
                .picture(request.getPicture())
                .build();
    }
}
