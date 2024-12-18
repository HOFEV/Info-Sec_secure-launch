package com.hofev.securelaunch.services;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

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

    public static void putKeyToFile(String key) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_LICENSE))) {
            writer.write(key);
        } catch (IOException e) {
            System.out.println("Ошибка при записи ключа");
        }
    }
}
