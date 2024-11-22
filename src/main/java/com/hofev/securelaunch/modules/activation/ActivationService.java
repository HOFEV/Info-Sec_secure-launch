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
        if (getKeyFromFile() == null) return false; // Если не получилось получить ключ

        // Сравнивает существующий ключ в файле с ключом активации для данного устройства
        return getKeyFromFile().equals(getKeyForDevice());
    }

    // Метод создает уникальный ключ для этого устройства
    private static String getKeyForDevice() {

        // Получаем серийный номер компютера
        String diskSerialNumber = getDiskSerialNumber();

        // Объединяется ключ и серийный номер диска и хэшируется
        return HashingUtil.hash256(diskSerialNumber + KEY_LICENSE_SHA_256);
    }

    // Метод для получения ключа из файла
    public static String getKeyFromFile() {
        File reFile = new File("license.bin"); // Файл с активированной лицензией
        String keyFile = null; // Ключ из файла

        try(BufferedReader reader = new BufferedReader(new FileReader(reFile))) {
            keyFile = reader.readLine();

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла!");
        }

        return keyFile;
    }

    // Метод выдает серийный номер диска
    public static String getDiskSerialNumber() {
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
