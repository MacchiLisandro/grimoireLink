package com.maliag.grimoireLink.features.users.exceptions;

import com.maliag.grimoireLink.common.exceptions.UnauthorizedException;

public class UserNotRegisteredOrPasswordIncorrectException extends UnauthorizedException {
    public UserNotRegisteredOrPasswordIncorrectException(String message) {
        super(message);
    }
}
