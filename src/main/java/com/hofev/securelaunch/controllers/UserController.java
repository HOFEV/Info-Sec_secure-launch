package com.hofev.securelaunch.controllers;

public class UserController {
    private static final UserController USER_CONTROLLER = new UserController();

    private UserController() {}

    public static UserController getInstance() {
        return USER_CONTROLLER;
    }

    public void startLoginModule() {

    }
}
