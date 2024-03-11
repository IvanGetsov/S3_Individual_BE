package individual.individual_backend.controller;

import individual.individual_backend.business.UserManager;
import individual.individual_backend.configuration.security.token.AccessToken;
import individual.individual_backend.configuration.security.token.exception.InvalidAccessTokenException;
import individual.individual_backend.configuration.security.token.impl.AccessTokenEncoderDecoderImpl;
import individual.individual_backend.controller.converters.UserConverter;
import individual.individual_backend.controller.dto.*;
import individual.individual_backend.domain.User;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {
    private final UserManager userManager;
    private final AccessTokenEncoderDecoderImpl tokenEncoderDecoder;

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request){
        if (userManager.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = UserConverter.convertCreateRequestToDomainUser(request);
        String password = user.getPassword();
        Integer userId = userManager.createUser(user, password);
        CreateUserResponse response = CreateUserResponse.builder()
                .userId(userId)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<GetUsersResponse> getUsers(){
        GetUsersResponse response = GetUsersResponse.builder()
                .users(userManager.getUsers())
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId){
        userManager.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed({"CAR_OWNER", "CAR_RENTER"})
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") Integer id,
                                          @RequestBody @Valid UpdateUserRequest request) {
        User existingUser = userManager.getUser(id);

        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        existingUser.setAge(request.getAge());
        existingUser.setName(request.getName());
        existingUser.setEmail(request.getEmail());
        userManager.updateUser(existingUser);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserByAccessToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
        String token = accessToken.replace("Bearer ", "");

        User user = decodeAccessTokenAndGetUser(token);


        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(user);
    }


    private User decodeAccessTokenAndGetUser(String accessToken) {
        try {
            AccessToken decodedToken = tokenEncoderDecoder.decode(accessToken);


            Integer userId = decodedToken.getUserId();


            return userManager.getUser(userId);
        } catch (InvalidAccessTokenException e) {
            return null;
        }
    }

    @PutMapping("/{id}/update-email")
    public ResponseEntity<Void> updateEmail(@PathVariable("id") Integer id,
                                            @RequestParam String email) {
        User existingUser = userManager.getUser(id);

        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        if (userManager.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        userManager.updateEmail(existingUser.getId(), email);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/update-password")
    public ResponseEntity<Void> updatePassword(@PathVariable("id") Integer id,
                                               @RequestParam String currentPassword,
                                               @RequestParam String newPassword) {
        User existingUser = userManager.getUser(id);

        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        if (!userManager.updatePassword(existingUser.getId(), currentPassword, newPassword)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/update-profile-picture")
    public ResponseEntity<Void> updateProfilePicture(@PathVariable("id") Integer id,
                                                     @RequestBody String picture) {
        userManager.saveProfilePicture(id, picture);
        return ResponseEntity.noContent().build();
    }
}
