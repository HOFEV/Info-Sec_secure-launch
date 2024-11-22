package com.hofev.securelaunch.controllers;

import com.hofev.securelaunch.modules.activation.ActivationForm;
import com.hofev.securelaunch.modules.activation.ActivationService;

public class MainController {

    private static final MainController MAIN_CONTROLLER = new MainController();

    private MainController() {}

    public static MainController getInstance() {
        return MAIN_CONTROLLER;
    }

    public void startApplication() {

        // Создает поле активации лицензии, если она не активирована
        if (!ActivationService.checkActivationLicense()) {
            ActivationForm.showLoginForm();
        }
    }

    // Активация приложения
    public boolean activatingApplication(String enteredKey) {

        // Проверка на совпадение ключа
        if (ActivationService.isLicenseKeyValid(enteredKey)) {
            // Создание/редактирование папки и добавления в него ключа
            // Вход в систему
            return true;
        } else {
            // Вывод ошибки: неверный ключ
            return false;
        }

    }
}
