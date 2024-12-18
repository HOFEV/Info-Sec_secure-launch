package com.hofev.securelaunch.controllers;

import com.hofev.securelaunch.exceptions.*;
import com.hofev.securelaunch.modules.blockingUsers.LoginAttemptService;
import com.hofev.securelaunch.modules.fileHistory.FileHistoryService;
import com.hofev.securelaunch.modules.userAccessLevel.AccessLevel;
import com.hofev.securelaunch.modules.userAccessLevel.AccessLevelService;
import com.hofev.securelaunch.repositories.FileObjRepository;
import com.hofev.securelaunch.repositories.UserRepository;
import com.hofev.securelaunch.services.FileService;
import com.hofev.securelaunch.services.UserService;
import com.hofev.securelaunch.views.EditorForm;
import com.hofev.securelaunch.views.LoginForm;
import com.hofev.securelaunch.views.RegistrationForm;
import com.hofev.securelaunch.views.UserAccountForm;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

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

    // Изменяет роль выбранного пользователя
    public void changeRoleForUser(String login, String newRole) {
        AccessLevelService.changeRoleUser(login, newRole);
    }

    public void startEditor() {
        EditorForm editorForm = new EditorForm();
    }

    // Открытие и подготовка файла к редактированию
    public void openFileObj(File file, EditorForm editorForm) {
        //Проверка файла в базе на совпадение хэша
        try {
            if (!FileHistoryService.CheckMatchOfHashFileContent(file)) {
                editorForm.showError("Данный файл был изменен вне программы, доступ запрещен!");
                return;
            }
        } catch (FileObjNotFoundException e) {
            try {
                (new FileObjRepository()).uploadFileObj(FileHistoryService.createFileObj(file));
                System.out.println("Файл добавлен");
            } catch (IOException ex) {
                editorForm.showError("Выбранный файл недоступен для чтения.");
                return;
            }
        } catch (IOException e) {
            editorForm.showError("Выбранный файл недоступен для чтения.");
            return;
        }

        // Отображение содержимого файла на экране
        try {
            editorForm.showText(FileService.readFileContent(file));
        } catch (IOException e) {
            editorForm.showError("Выбранный файл недоступен для чтения.");
            return;
        }

        // Обновление текущего файла
        editorForm.updateCurrentFile(file);

        // Активируется кнопка редактирования
        editorForm.setEnableEditButton(true);

        // Кнопка сохранения пока не доступна
        editorForm.setEnableSaveButton(false);

        // Поле редактирования пока заблокировано
        editorForm.enableEditing(false);
    }

    // Редактирование файла
    public void editFileObj(EditorForm editorForm) {

        // Открытие доступа к изменению текста
        editorForm.enableEditing(true);

        // Активируется кнопка сохранения документа
        editorForm.setEnableSaveButton(true);

        // Деактивируется кнопка редактирования
        editorForm.setEnableEditButton(false);
    }

    // Сохранение измений в файле
    public void saveFileObj(File currentFile, EditorForm editorForm) {

        // Получение обновленного текста
        String updatedText = editorForm.getText();

        // Блокирует поле для изменения текста
        editorForm.enableEditing(false);

        // Выключает кнопку сохранения
        editorForm.setEnableSaveButton(false);

        // Перезапись текста в файл
        try {
            // Перезапись текста в файл
            FileService.writeFileContent(currentFile, updatedText);

            // Обновление хэш содержимого файла
            FileHistoryService.updateHashFileContent(currentFile, updatedText);

        } catch (IOException e) {
            editorForm.showError("Ошибка при сохранении файла!" + e.getMessage());
        } catch (FileObjNotFoundException e) {
            editorForm.showError("Выбранный файл не существует!" + e.getMessage());
        }

        // Очищает поле текста
        editorForm.showText("");
    }
}
