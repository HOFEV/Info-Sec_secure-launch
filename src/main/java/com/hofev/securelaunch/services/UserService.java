package com.hofev.securelaunch.services;

import com.hofev.securelaunch.exceptions.InvalidPasswordException;
import com.hofev.securelaunch.exceptions.UserNotFoundException;
import com.hofev.securelaunch.models.User;
import com.hofev.securelaunch.repositories.UserRepository;
import com.hofev.securelaunch.utils.HashingUtil;

public class UserService {


    // Вход пользователя
    public static void loginUser(String login, String password) throws UserNotFoundException, InvalidPasswordException {
        UserRepository userRepository = new UserRepository();
        User user = userRepository.findUserByLogin(login);

        if (!(user.getPassword().equals(HashingUtil.hash256(password)))) throw new InvalidPasswordException("Не подходит пароль!");
    }
}
