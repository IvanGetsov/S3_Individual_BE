package individual.individual_backend.business.converters;

import individual.individual_backend.domain.User;
import individual.individual_backend.persistence.entity.UserEntity;

public class UsersConvertor {
private UsersConvertor(){

}
    public static UserEntity toEntity(User user){
        if (user == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setPassword(user.getPassword());
        userEntity.setAge(user.getAge());
        userEntity.setEmail(user.getEmail());
        userEntity.setName(user.getName());
        userEntity.setRole(user.getRole());
        userEntity.setPicture(user.getPicture());
        return userEntity;
    }
     public static User toDomain(UserEntity userEntity){
         if (userEntity == null) {
             return null;
         }
        User user = new User();
        user.setId(userEntity.getId());
        user.setPassword(userEntity.getPassword());
        user.setAge(userEntity.getAge());
        user.setEmail(userEntity.getEmail());
        user.setName(userEntity.getName());
        user.setRole(userEntity.getRole());
        user.setPicture(userEntity.getPicture());
        return user;
     }
}
