package com.hofev.securelaunch.models;

import com.hofev.securelaunch.modules.userAccessLevel.AccessLevel;

import java.io.Serializable;

public class User implements Serializable {
    private String login;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;
    private AccessLevel userLevelAccess = AccessLevel.USER;

    public User(String login, String name, String surname, String phone, String email, String password) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccessLevel getUserLevelAccess() {
        return userLevelAccess;
    }

    public void setUserLevelAccess(AccessLevel access) {
        this.userLevelAccess = access;
    }
}
