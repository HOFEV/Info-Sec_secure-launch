package com.hofev.securelaunch.controllers;

import com.hofev.securelaunch.exceptions.InvalidPasswordException;
import com.hofev.securelaunch.exceptions.UserAlreadyExistException;
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

    // Регистрация пользователя
    public void registrationUser(String[] dataUser, JFrame frame) {
        try {
            UserService.getInstance().registrationUser(dataUser);
            frame.dispose();
            startLoginUser();
        } catch (UserAlreadyExistException e) {
            RegistrationForm.printErrorRegistration(frame);
        }
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
            frame.dispose();
            startUserAccount(login);
        } catch (UserNotFoundException | InvalidPasswordException e) {
            LoginForm.printErrorLogin(frame);
        }
    }

    // Запуск личного кабинета пользователя
    public void startUserAccount(String login) {

       UserAccountForm userAccountForm = new UserAccountForm(UserService.getInstance().getDataFromUser(login));
        userAccountForm.show();
    }

}
