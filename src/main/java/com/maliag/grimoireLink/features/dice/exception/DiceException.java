package com.maliag.grimoireLink.features.dice.exception;

import com.maliag.grimoireLink.common.exceptions.ConflictException;

public class DiceException extends ConflictException {
    public DiceException(String message) {
        super(message);
    }
}
