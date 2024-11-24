package com.hofev.securelaunch.services;

import com.hofev.securelaunch.exceptions.InvalidPasswordException;
import com.hofev.securelaunch.exceptions.UserNotFoundException;
import com.hofev.securelaunch.models.User;
import com.hofev.securelaunch.repositories.UserRepository;
import com.hofev.securelaunch.utils.HashingUtil;

public class UserService {


    // Процесс входа пользователя в систему
    private static boolean isLoginUser(String login, String password) throws UserNotFoundException, InvalidPasswordException {
        UserRepository userRepository = new UserRepository();
        User user = userRepository.findUserByLogin(login);

        if (user.getPassword().equals(HashingUtil.hash256(password))) {
            return true;
        } else {
            throw new InvalidPasswordException("Не подходит пароль!");
        }
    }
}
