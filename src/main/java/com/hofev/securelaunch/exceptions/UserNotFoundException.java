package com.hofev.securelaunch.exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}
