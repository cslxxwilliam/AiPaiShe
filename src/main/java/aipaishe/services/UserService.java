package aipaishe.services;

import aipaishe.models.EmailExistsException;
import aipaishe.models.User;
import aipaishe.services.repositories.UserDao;
import aipaishe.models.UserDto;
import aipaishe.models.userregistration.VerificationToken;
import aipaishe.services.repositories.VerificationTokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by williamxuxianglin on 19/6/17.
 */
@Service
public class UserService implements IUserService {
    @Autowired
    private UserDao userRepository;

    @Autowired
    private VerificationTokenDao verificationTokenRepository;

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
        userRepository.create(user);
        return user;
    }

    @Override
    public void saveVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(token, user);

        verificationTokenRepository.create(verificationToken);
    }

    private boolean emailExist(String email) {
        User user = userRepository.getByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
}