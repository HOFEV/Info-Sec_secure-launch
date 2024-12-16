package com.hofev.securelaunch.modules.userAccessLevel;

import com.hofev.securelaunch.exceptions.UserAlreadyExistException;
import com.hofev.securelaunch.exceptions.UserNotFoundException;
import com.hofev.securelaunch.models.User;
import com.hofev.securelaunch.repositories.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccessLevelService {
    private AccessLevelService() {}

    // Инициализация аккаунта админа
    public static void initAdminAcc() {

        try {
            (new UserRepository()).findUserByLogin("admin");
        } catch (UserNotFoundException e) {
            createDefaultAdminAcc();
        }
    }

    //Предоставляет список доступных ролей
    public static String[] getUserRoles() {

        String[] userRules = new String[AccessLevel.values().length];
        int i = 0; // Счетчик

        for (AccessLevel accessLevel : AccessLevel.values()) {
            userRules[i++] = accessLevel.name();
        }
        return userRules;
    }

    // Список с разрешенными для изменения ролями
    public static String[] getListOfAvailableRolesToChange() {
        List<String> userAvailableRolesList = new ArrayList<>();
        for (String role : getUserRoles()) {
            if (AccessLevel.valueOf(role).isSettingRight()) userAvailableRolesList.add(role);
        }

        int i = 0; // Счетчик
        String[] userAvailableRoles = new String[userAvailableRolesList.size()];
        for (String roles : userAvailableRolesList) {
            userAvailableRoles[i++] = roles;
        }

        return userAvailableRoles;
    }

    // Создает начальный аккаунт админа
    private static void createDefaultAdminAcc() {
        try {
            User admin = new User(
                    "admin",
                    "Иван",
                    "Иванов",
                    "",
                    "@gmail.com",
                    "9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0"
            );
            admin.setUserLevelAccess(AccessLevel.ADMIN);
            (new UserRepository()).saveUser(admin);
        } catch (UserAlreadyExistException _) {
        }
    }
}
