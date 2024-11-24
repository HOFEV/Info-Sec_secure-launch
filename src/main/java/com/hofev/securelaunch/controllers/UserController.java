package com.hofev.securelaunch.controllers;

import com.hofev.securelaunch.views.LoginForm;
import com.hofev.securelaunch.views.RegistrationForm;
import com.hofev.securelaunch.views.UserAccountForm;

public class UserController {
    private static final UserController USER_CONTROLLER = new UserController();

    private UserController() {}

    public static UserController getInstance() {
        return USER_CONTROLLER;
    }

    // Переход на форму входа
    public void startLoginUser() {

        // Отображение поля входа
        // Графические интерфейсы пользователя
        LoginForm loginForm = new LoginForm();
        loginForm.show();
    }

    public void loginUser() {

    }

    // Переход на форму регистрации
    public void startRegistration() {

        // Обображение поля регистрации
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.show();
    }

    // Переход на форму личного кибинета
    public void startUserAccount() {
//        UserAccountForm userAccountForm = new UserAccountForm();
//        userAccountForm.show();
    }

}
