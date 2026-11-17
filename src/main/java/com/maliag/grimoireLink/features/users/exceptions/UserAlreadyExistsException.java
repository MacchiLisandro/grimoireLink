package com.maliag.grimoireLink.features.users.exceptions;

import com.maliag.grimoireLink.common.exceptions.ConflictException;

public class UserAlreadyExistsException extends ConflictException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
