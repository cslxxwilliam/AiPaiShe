package aipaishe.services;

import aipaishe.models.EmailExistsException;
import aipaishe.models.User;
import aipaishe.models.UserDto;
import aipaishe.models.userregistration.VerificationToken;

/**
 * Created by williamxuxianglin on 19/6/17.
 */
public interface IUserService {
    User registerNewUserAccount(UserDto accountDto)
            throws EmailExistsException;

    void saveVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String token);

    void saveRegisteredUser(User user);
}