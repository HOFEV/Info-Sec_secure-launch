package com.hofev.securelaunch.controllers;

import com.hofev.securelaunch.views.ActivationForm;
import com.hofev.securelaunch.modules.activation.ActivationService;
import com.hofev.securelaunch.services.FileService;

import javax.swing.*;

public class MainController {

    private static final MainController MAIN_CONTROLLER = new MainController();

    private MainController() {}

    public static MainController getInstance() {
        return MAIN_CONTROLLER;
    }

    public void startApplication() {

        // Создает поле активации лицензии, если она не активирована
        if (!ActivationService.checkActivationLicense()) {
            ActivationForm.showActivationForm();
        }

        // Если приложение активировано, запускается инициализаци пользователя
        UserController.getInstance().startLoginUser();
    }

    // Активация приложения
    public boolean activatingApplication(String enteredKey, JFrame frame) {

        // Проверка на совпадение ключа
        if (ActivationService.isLicenseKeyValid(enteredKey)) {

            // Создание/редактирование папки и добавления в него ключа
            FileService.putKeyToFile(ActivationService.getKeyForDevice());
            frame.dispose(); // Закрытие окна после успешной активации

            // Вход в систему
            UserController.getInstance().startLoginUser();

            return true;
        } else {
            // Вывод ошибки: неверный ключ
            return false;
        }

    }
}
