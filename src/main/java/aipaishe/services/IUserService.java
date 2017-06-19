package aipaishe.services;

import aipaishe.models.EmailExistsException;
import aipaishe.models.User;
import aipaishe.models.UserDto;

/**
 * Created by williamxuxianglin on 19/6/17.
 */
public interface IUserService {
    User registerNewUserAccount(UserDto accountDto)
            throws EmailExistsException;
}