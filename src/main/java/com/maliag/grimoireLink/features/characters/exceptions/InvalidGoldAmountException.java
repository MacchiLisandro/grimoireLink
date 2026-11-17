package com.maliag.grimoireLink.features.characters.exceptions;

import com.maliag.grimoireLink.common.exceptions.ConflictException;

public class InvalidGoldAmountException extends ConflictException {
    public InvalidGoldAmountException(String message) {
        super(message);
    }
}