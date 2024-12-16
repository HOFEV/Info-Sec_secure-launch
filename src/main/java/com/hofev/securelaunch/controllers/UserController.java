package com.hofev.securelaunch.controllers;

import com.hofev.securelaunch.exceptions.InvalidPasswordException;
import com.hofev.securelaunch.exceptions.LoginBlockedException;
import com.hofev.securelaunch.exceptions.UserAlreadyExistException;
import com.hofev.securelaunch.exceptions.UserNotFoundException;
import com.hofev.securelaunch.modules.blockingUsers.LoginAttemptService;
import com.hofev.securelaunch.modules.userAccessLevel.AccessLevel;
import com.hofev.securelaunch.modules.userAccessLevel.AccessLevelService;
import com.hofev.securelaunch.repositories.UserRepository;
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
    public void startRegistrationForm() {

        // Отображение поля регистрации
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.show();
    }

    // Регистрация пользователя
    public void registrationUser(String[] dataUser, JFrame frame) {
        try {
            UserService.getInstance().registrationUser(dataUser);
            frame.dispose();
            startLoginUserForm();
        } catch (UserAlreadyExistException e) {
            RegistrationForm.printErrorRegistration(frame);
        }
    }

    // Запуск формы для входа
    public void startLoginUserForm() {

        // Отображение поля входа
        // Графические интерфейсы пользователя
        new LoginForm();
    }

    // Вход пользователя
    public void loginUser(String login, String password, JFrame frame) {
        LoginAttemptService loginAttemptService = LoginAttemptService.getInstance();
        try {
            UserService.getInstance().loginUser(login, password);
            frame.dispose();
            startUserAccount(login);
        } catch (UserNotFoundException | InvalidPasswordException e) {
            loginAttemptService.incrementAttempts();
            LoginForm.printErrorLogin(frame, loginAttemptService.getRemainingAttempts());
            if (loginAttemptService.getRemainingAttempts() == 0) loginAttemptService.resetAttempts();
        } catch (LoginBlockedException e) {
            LoginForm.printErrorBlockedLogin(frame, loginAttemptService.getRemainingLockTime());
        }
    }

    // Запуск личного кабинета пользователя
    public void startUserAccount(String login) {

        UserAccountForm userAccountForm = new UserAccountForm(UserService.getInstance().getDataFromUser(login));
        userAccountForm.show();
    }

    // Предоставляет разрешение на работу с редактором
    public boolean getAccessForEdit(String userAccessLevel) {
        return AccessLevel.valueOf(userAccessLevel).isEditingRight();
    }

    // Предоставляет список всех пользователей
    public String[] getUserList() {
        return (new UserRepository()).getUserList();
    }

    // Предоставляет все доступные для изменения роли
    public String[] getAvailableUserRules() {
        return AccessLevelService.getListOfAvailableRolesToChange();
    }
}
