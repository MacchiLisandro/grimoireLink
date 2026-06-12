package com.maliag.grimoireLink.features.characters.exceptions;

import com.maliag.grimoireLink.common.exceptions.ConflictException;

public class InvalidLevelException extends RuntimeException {
    public InvalidLevelException(String message) {
        super(message);
    }
}
