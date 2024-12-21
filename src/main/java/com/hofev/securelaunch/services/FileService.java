package com.hofev.securelaunch.services;

import com.hofev.securelaunch.modules.fileHistory.FileSettings;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FileService {
    private static final File FILE_LICENSE = new File("license.bin"); // Файл с активированной лицензией

    // Метод для получения ключа из файла
    public static String getKeyFromFile() {

        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_LICENSE))) {
            return reader.readLine(); // Ключ из файла

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла!");
        }

        return null;
    }

    // Копирует содержимое текстового файла
    public static String readFileContent(File file) throws IOException {
        if (!(file.exists() && file.isFile() && file.canRead())) throw new IOException("Файл невозможно прочитать");
        // Используем Files.readString для удобства и надёжности
        return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    }

    // Перезапись текста в файл
    public static void updateFileContent(File file, String content) throws IOException {

        // Используем Files.write с опциями CREATE и TRUNCATE_EXISTING,
        // чтобы при необходимости файл был создан, либо перезаписан.
        Files.writeString(
                file.toPath(),
                content,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    // Изменяет расширение файла на указанное в настройках файла FileSettings и возвращает File
    public static File updateFileExtension(File file) throws IOException {

        // Получаем имя файла
        String fileName = file.getName();

        // Находим индекс последней точки, чтобы отделить расширение
        int dotIndex = fileName.lastIndexOf('.');

        // Если расширения нет, то используем полное имя как базовое
        String baseName = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);

        // Формируем имя с новым расширением
        File newFile = new File(file.getParent(), baseName + FileSettings.FILE_EXTENSION);

        // Копирует содержимое старого файла и вставляет в новый файл с новым расширением
        updateFileContent(newFile, readFileContent(file));

        // Удаляет файл со старым расширением
        if (!file.delete()) {
            System.out.println("Ошибка при удалении старого файла");
        }

        return newFile;
    }

    // Проверка на совпадение расширения с настройками файла
    public static boolean checkValidFileExtension (File file) {

        // Получаем текущее расширение файла
        String fileExtension = getFileExtension(file);

        // Если нет у файла расширения
        if (fileExtension == null) return false;

        return fileExtension.equals(FileSettings.FILE_EXTENSION);
    }

    // Возвращает расширение файла
    private static String getFileExtension(File file) {
        if (file == null || !file.exists()) return null;

        String fileName = file.getName();

        // Находим индекс последней точки, чтобы отделить расширение
        int dotIndex = fileName.lastIndexOf('.');

        // Если точка найдена и она не в начале или конце имени файла
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1); // Возвращаем часть после последней точки
        }

        return null;
    }


    public static void putKeyToFile(String key) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_LICENSE))) {
            writer.write(key);
        } catch (IOException e) {
            System.out.println("Ошибка при записи ключа");
        }
    }
}
