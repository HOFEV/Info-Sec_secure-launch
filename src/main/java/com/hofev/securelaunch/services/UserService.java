package com.hofev.securelaunch.services;

import com.hofev.securelaunch.exceptions.InvalidPasswordException;
import com.hofev.securelaunch.exceptions.LoginBlockedException;
import com.hofev.securelaunch.exceptions.UserAlreadyExistException;
import com.hofev.securelaunch.exceptions.UserNotFoundException;
import com.hofev.securelaunch.models.User;
import com.hofev.securelaunch.modules.blockingUsers.LoginAttemptService;
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
    public void loginUser(String login, String password) throws UserNotFoundException, InvalidPasswordException, LoginBlockedException {
        if (LoginAttemptService.getInstance().isLocked()) throw new LoginBlockedException("Доступ заблокирован");
        User user = userRepository.findUserByLogin(login);

        if (!(user.getPassword().equals(HashingUtil.hash256(password)))) throw new InvalidPasswordException("Не подходит пароль!");
    }

    // Регистрация пользователя
    public void registrationUser(String[] dataUser) throws UserAlreadyExistException {

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
                    HashingUtil.hash256(dataUser[5])
            ));
        }
    }

    // Передает данные о пользователе
    public String[] getDataFromUser(String login) {

        try {
            User user = userRepository.findUserByLogin(login);
            return new String[] {
                    user.getName(),
                    user.getSurname(),
                    user.getPhone(),
                    user.getEmail(),
                    user.getUserLevelAccess().name()
            };
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
