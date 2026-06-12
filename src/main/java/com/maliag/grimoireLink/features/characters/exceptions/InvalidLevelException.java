package com.maliag.grimoireLink.features.characters.exceptions;

import com.maliag.grimoireLink.common.exceptions.ConflictException;

public class InvalidLevelException extends ConflictException {
    public InvalidLevelException(String message) {
        super(message);
    }
}
