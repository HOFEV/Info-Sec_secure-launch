package com.hofev.securelaunch.modules.userAccessLevel;

import com.hofev.securelaunch.exceptions.UserAlreadyExistException;
import com.hofev.securelaunch.exceptions.UserNotFoundException;
import com.hofev.securelaunch.models.User;
import com.hofev.securelaunch.repositories.UserRepository;

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
