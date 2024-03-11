package individual.individual_backend.business;

import individual.individual_backend.domain.User;
import java.util.List;

public interface UserManager {
    Integer createUser(User user, String password);
    List<User> getUsers();
    User getUser(Integer userId);
    void deleteUser(int userId);
    void updateUser(User user);
    boolean existsByEmail(String email);
    boolean updateEmail(Integer userId, String newEmail);
    boolean updatePassword(Integer userId, String currentPassword, String newPassword);
    void saveProfilePicture(Integer userId, String profilePicture);
}
