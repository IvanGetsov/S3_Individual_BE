package individual.individual_backend.controller;

import individual.individual_backend.business.UserManager;
import individual.individual_backend.configuration.security.token.AccessToken;
import individual.individual_backend.configuration.security.token.impl.AccessTokenImpl;
import individual.individual_backend.domain.Role;
import individual.individual_backend.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserManager manager;

    private String picture;
    @Test
    @WithMockUser(username = "ivan", roles = "CAR_OWNER")
    void createUser_shouldReturn201WithId_whenUserCreated() throws Exception {
        when(manager.createUser(any(User.class), any(String.class))).thenReturn(1);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": "Ivan",
                            "email": "test@example.com",
                            "password": "password",
                            "role": "CAR_OWNER"
                        }
                        """)
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                    {
                        "userId": 1
                    }
                """));

        verify(manager).createUser(any(User.class), any(String.class));
    }


    @Test
    @WithMockUser(username = "ivan", roles = "CAR_OWNER")
    void getUsers_shouldReturnListOfUsers() throws Exception {
        List<User> userList = Arrays.asList(
                new User("test1@example.com", "password1", 1, "User1", 25, Role.CAR_OWNER, picture),
                new User("test2@example.com", "password2", 2, "User2", 30, Role.CAR_OWNER, picture)
        );

        when(manager.getUsers()).thenReturn(userList);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.users").isArray())
                .andExpect(jsonPath("$.users[0].id").value(1))
                .andExpect(jsonPath("$.users[1].id").value(2));

        verify(manager).getUsers();
    }


    @Test
    @WithMockUser(username = "ivan", roles = "CAR_OWNER")
    void deleteUser_shouldReturnNoContent() throws Exception {
        int userId = 1;

        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isNoContent());

        verify(manager).deleteUser(userId);
    }




    @Test
    @WithMockUser(username = "ivan", roles = "CAR_OWNER")
    void updateUser_shouldReturnNoContent() throws Exception {
        int userId = 1;

        when(manager.getUser(userId)).thenReturn(new User("existing@example.com", "password", userId, "ExistingUser", 25, Role.CAR_OWNER, picture));

        doNothing().when(manager).updateUser(any(User.class));

        mockMvc.perform(put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "name": "UpdatedUser",
                    "email": "updated@example.com",
                    "password": "newpassword",
                    "role": "CAR_OWNER"
                }
            """)
                )
                .andExpect(status().isNoContent());

        verify(manager).updateUser(any(User.class));
    }

    @Test
    @WithMockUser(username = "ivan", roles = "CAR_OWNER")
    void getUserByAccessToken_shouldReturnUnauthorized() throws Exception {
        String accessToken = "invalid-access-token";

        when(manager.getUser(any(Integer.class))).thenReturn(null);

        mockMvc.perform(get("/users/profile")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isUnauthorized());

        verify(manager, never()).getUser(any(Integer.class));
    }

    @Test
    @WithMockUser(username = "ivan", roles = "CAR_OWNER")
    void createUser_withConflictingEmail_shouldReturnConflict() throws Exception {
        String conflictingEmail = "conflict@example.com";
        when(manager.existsByEmail(conflictingEmail)).thenReturn(true);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "name": "ConflictingUser",
                        "email": "%s",
                        "password": "password",
                        "role": "CAR_OWNER"
                    }
                """.formatted(conflictingEmail))
                )
                .andExpect(status().isConflict());

        verify(manager, never()).createUser(any(User.class), any(String.class));
    }



}