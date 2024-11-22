package com.hofev.securelaunch.modules.activation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashingUtil {
    private HashingUtil() {}

    // Хэширует входящую строку
    public static String hash256(String input) {
        try {
            // Создаем экземпляр MessageDigest с алгоритмом SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Преобразуем входную строку в массив байтов
            byte[] encodedHash = digest.digest(input.getBytes());

            // Преобразуем хэш-значение в шестнадцатеричную строку
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка: алгоритм SHA-256 недоступен", e);
        }
    }
}
