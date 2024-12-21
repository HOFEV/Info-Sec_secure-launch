package com.hofev.securelaunch.services;

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
        // Получаем имя файла
        String fileName = file.getName();
        // Находим индекс последней точки, чтобы отделить расширение
        int dotIndex = fileName.lastIndexOf('.');
        // Если расширения нет, то используем полное имя как базовое
        String baseName = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);

        // Формируем имя с новым расширением
        File newFile = new File(file.getParent(), baseName + ".sec");

        // Используем Files.write с опциями CREATE и TRUNCATE_EXISTING,
        // чтобы при необходимости файл был создан, либо перезаписан.
        Files.writeString(newFile.toPath(), content, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }


    public static void putKeyToFile(String key) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_LICENSE))) {
            writer.write(key);
        } catch (IOException e) {
            System.out.println("Ошибка при записи ключа");
        }
    }
}
