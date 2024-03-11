package individual.individual_backend.business.impl;

import individual.individual_backend.business.UserManager;
import individual.individual_backend.business.converters.UsersConvertor;
import individual.individual_backend.domain.User;
import individual.individual_backend.persistence.UserRepository;
import individual.individual_backend.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@AllArgsConstructor
@Service
public class UserManagerImpl implements UserManager {
    private UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    /**
     * @return an integer when a user is created
     * @should return a unique id when user is created
     */
    @Override
    public Integer createUser(User user, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        UserEntity userEntity = UsersConvertor.toEntity(user);
        UserEntity savedUser = repository.save(userEntity);
        return savedUser.getId();
    }
    /**
     * @return an arrayList when asked for users
     * @should return an empty arrayList when no users are present
     * @should return an arrayList with cars when users are present
     */
    @Override
    public List<User> getUsers() {
        return repository.findAll().stream().map(UsersConvertor::toDomain).toList();
    }

    @Override
    public User getUser(Integer userId) {
        Optional<UserEntity> userEntityOptional = repository.findById(userId);

        if(userEntityOptional.isPresent()){
            UserEntity userEntity = userEntityOptional.get();
            return UsersConvertor.toDomain(userEntity);
        }
        else{
            return null;
        }
    }
    /**
     * @param userId The ID of the user to delete.
     * @should remove the user with the given ID from the repository
     */
    @Override
    public void deleteUser(int userId) {repository.deleteById(userId);}
    /**
     *
     * @param user The user object containing the updated information.
     *@should update an existing user's details if it exists in the repository
     */
    @Override
    public void updateUser(User user) {
        Optional<UserEntity> userEntityOptional = repository.findById(user.getId());
        if (userEntityOptional.isPresent()){
            UserEntity userToUpdate = userEntityOptional.get();
            updateUserFields(userToUpdate, user);
            repository.save(userToUpdate);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    private void updateUserFields(UserEntity existingUser, User newUser) {
        existingUser.setAge(newUser.getAge());
        existingUser.setName(newUser.getName());
        existingUser.setPicture(newUser.getPicture());

        if (!isEmpty(newUser.getPassword())){
            existingUser.setPassword(newUser.getPassword());
        }
    }

    public boolean updateEmail(Integer userId, String newEmail) {
        Optional<UserEntity> userEntityOptional = repository.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity userToUpdate = userEntityOptional.get();

            if (!userToUpdate.getEmail().equals(newEmail) && repository.existsByEmail(newEmail)) {
                return false;
            }

            userToUpdate.setEmail(newEmail);
            repository.save(userToUpdate);
            return true;
        }
        return false;
    }

    public boolean updatePassword(Integer userId, String currentPassword, String newPassword) {
        Optional<UserEntity> userEntityOptional = repository.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity userToUpdate = userEntityOptional.get();

            if (passwordEncoder.matches(currentPassword, userToUpdate.getPassword())) {
                String encodedNewPassword = passwordEncoder.encode(newPassword);
                userToUpdate.setPassword(encodedNewPassword);
                repository.save(userToUpdate);
                return true;
            }
        }
        return false;
    }

    @Override
    public void saveProfilePicture(Integer userId, String profilePicture) {
        UserEntity userEntity = repository.findById(userId).orElse(null);

        if (userEntity != null) {
            userEntity.setPicture(profilePicture);
            repository.saveAndFlush(userEntity);
        }
    }


}
