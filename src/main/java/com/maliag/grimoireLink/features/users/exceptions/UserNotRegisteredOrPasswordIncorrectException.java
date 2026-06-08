package com.maliag.grimoireLink.features.users.exceptions;

public class UserNotRegisteredOrPasswordIncorrectException extends RuntimeException {
    public UserNotRegisteredOrPasswordIncorrectException(String message) {
        super(message);
    }
}
