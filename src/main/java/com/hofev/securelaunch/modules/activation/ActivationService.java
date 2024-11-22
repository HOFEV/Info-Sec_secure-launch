package com.hofev.securelaunch.modules.activation;

// Реализует логику 1 лр

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class ActivationService {
    private static final String KEY_LICENSE_SHA_256 = "843f6eb0d196bac28f2106a9d0f8441f9518d6955d468424926063aeea9b59a4";

    // Проверка на активацию лицензии
    public static boolean checkActivationLicense() {
        if (FileService.getKeyFromFile() == null) return false; // Если не получилось получить ключ

        // Сравнивает существующий ключ в файле с ключом активации для данного устройства
        return FileService.getKeyFromFile().equals(getKeyForDevice());
    }

    // Проверка на соответсвие введеного ключа лицензионному
    public static boolean isLicenseKeyValid(String enteredKey) {
        return HashingUtil.hash256(enteredKey).equals(KEY_LICENSE_SHA_256);
    }

    // Метод создает уникальный ключ для этого устройства
    public static String getKeyForDevice() {

        // Получаем серийный номер компютера
        String diskSerialNumber = getDiskSerialNumber();

        // Объединяется ключ и серийный номер диска и хэшируется
        return HashingUtil.hash256(diskSerialNumber + KEY_LICENSE_SHA_256);
    }

    // Метод выдает серийный номер диска
    private static String getDiskSerialNumber() {
        try {
            Process process = Runtime.getRuntime().exec("wmic diskDrive get serialNumber");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.contains("SerialNumber")) return line.trim();
            }
        } catch (Exception e) {
            System.out.println("Ошибка получения серийного номера");
            return null;
        }

        return null;
    }

}
