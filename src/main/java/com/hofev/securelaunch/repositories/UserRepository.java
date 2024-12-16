package com.hofev.securelaunch.repositories;

import com.hofev.securelaunch.exceptions.UserAlreadyExistException;
import com.hofev.securelaunch.exceptions.UserNotFoundException;
import com.hofev.securelaunch.models.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final Map<String ,User> USER_LIST; // Список пользователей
    private final String nameOfFileUsers = "users.ser"; // Название файла, в котором будут хранится пользователи

    public UserRepository() {
        this.USER_LIST = loadUsersFromFile();
    }

    // Добавление пользователя в базу
    public void saveUser(User user) throws UserAlreadyExistException {
        try {
            if (findUserByLogin(user.getLogin()) != null) throw new UserAlreadyExistException("Пользователь уже существует");
        } catch (UserNotFoundException e) {
            USER_LIST.put(user.getLogin(), user);
        }
        saveUsersToFile();
    }

    // Получение списка всех логинов пользователей
    public String[] getUserList() {
        return USER_LIST.keySet().toArray(new String[0]);
    }

    // Поиск пользователя по логину
    public User findUserByLogin(String login) throws UserNotFoundException {
        if (USER_LIST.containsKey(login)) return USER_LIST.get(login);
        else throw new UserNotFoundException("Данного пользователя не существует!");
    }

    private Map<String, User> loadUsersFromFile() {
        // Десериализация пользователей
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nameOfFileUsers))) {

            Object obj = ois.readObject();

            if (obj instanceof Map) {
                return (Map<String, User>) obj;
            } else {
                throw new IllegalArgumentException("Ошибка загрузки пользователей");
            }

        } catch (IOException | ClassNotFoundException _) {
            return new HashMap<>();
        }
    }

    private void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nameOfFileUsers))) {
            oos.writeObject(USER_LIST);
        } catch (IOException e) {
            System.out.println("Ошибка записи пользователей");
        }
    }

}