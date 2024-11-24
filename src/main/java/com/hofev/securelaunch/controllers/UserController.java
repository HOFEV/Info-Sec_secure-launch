package com.hofev.securelaunch.controllers;

import com.hofev.securelaunch.exceptions.InvalidPasswordException;
import com.hofev.securelaunch.exceptions.UserNotFoundException;
import com.hofev.securelaunch.services.UserService;
import com.hofev.securelaunch.views.LoginForm;
import com.hofev.securelaunch.views.RegistrationForm;
import com.hofev.securelaunch.views.UserAccountForm;

import javax.swing.*;

public class UserController {
    private static final UserController USER_CONTROLLER = new UserController();

    private UserController() {}

    public static UserController getInstance() {
        return USER_CONTROLLER;
    }

    // Создание формы регистрации
    public void startRegistration() {

        // Обображение поля регистрации
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.show();
    }

    // Запуск формы для входа
    public void startLoginUser() {

        // Отображение поля входа
        // Графические интерфейсы пользователя
        LoginForm loginForm = new LoginForm();
        loginForm.show();
    }

    // Вход пользователя
    public void loginUser(String login, String password, JFrame frame) {
        try {
            UserService.getInstance().loginUser(login, password);
            startUserAccount();
        } catch (UserNotFoundException | InvalidPasswordException e) {
            LoginForm.printErrorLogin(frame);
        }
    }

    // Запуск личного кабинета пользователя
    public void startUserAccount() {
//        UserAccountForm userAccountForm = new UserAccountForm();
//        userAccountForm.show();
    }

}
