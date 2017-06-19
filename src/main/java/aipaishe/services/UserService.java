package aipaishe.services;

import aipaishe.models.EmailExistsException;
import aipaishe.models.User;
import aipaishe.models.UserDao;
import aipaishe.models.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * Created by williamxuxianglin on 19/6/17.
 */
@Service
public class UserService implements IUserService {
    @Autowired
    private UserDao repository;

    @Transactional
    @Override
    public User registerNewUserAccount(UserDto accountDto)
            throws EmailExistsException {

        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email address:"  + accountDto.getEmail());
        }
        User user = new User();
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(accountDto.getPassword());
        user.setEmail(accountDto.getEmail());
//        user.setRoles(Arrays.asList("ROLE_USER"));
        repository.create(user);
        return user;
    }
    private boolean emailExist(String email) {
        User user = repository.getByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
}