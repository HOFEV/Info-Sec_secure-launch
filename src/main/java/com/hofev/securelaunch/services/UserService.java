package com.hofev.securelaunch.services;

import com.hofev.securelaunch.exceptions.InvalidPasswordException;
import com.hofev.securelaunch.exceptions.UserAlreadyExistException;
import com.hofev.securelaunch.exceptions.UserNotFoundException;
import com.hofev.securelaunch.models.User;
import com.hofev.securelaunch.repositories.UserRepository;
import com.hofev.securelaunch.utils.HashingUtil;

public class UserService {
    private static final UserService USER_SERVICE = new UserService();
    private final UserRepository userRepository = new UserRepository();

    private UserService() {}

    public static UserService getInstance() {
        return USER_SERVICE;
    }


    // Вход пользователя
    public void loginUser(String login, String password) throws UserNotFoundException, InvalidPasswordException {
        User user = userRepository.findUserByLogin(login);

        if (!(user.getPassword().equals(HashingUtil.hash256(password)))) throw new InvalidPasswordException("Не подходит пароль!");
    }

    // Регистрация пользователя
    public void registrationUser(String[] dataUser) throws UserAlreadyExistException {
//        String login;
//        String name;
//        String surname;
//        String phone;
//        String email;
//        String password;

        try {
            userRepository.findUserByLogin(dataUser[0]);
            throw new UserAlreadyExistException("Пользователь с таким логином существует!");
        } catch (UserNotFoundException e) {
            userRepository.saveUser(new User(
                    dataUser[0],
                    dataUser[1],
                    dataUser[2],
                    dataUser[3],
                    dataUser[4],
                    dataUser[5]
            ));
        }

    }
}
