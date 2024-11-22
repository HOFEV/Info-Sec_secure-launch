package com.hofev.securelaunch;

import com.hofev.securelaunch.controllers.MainController;

public class AppLauncher {
    public static void main(String[] args) {
        MainController.getInstance().startApplication();
    }
}