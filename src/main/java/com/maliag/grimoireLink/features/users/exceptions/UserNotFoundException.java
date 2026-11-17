package com.maliag.grimoireLink.features.users.exceptions;

import com.maliag.grimoireLink.common.exceptions.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
