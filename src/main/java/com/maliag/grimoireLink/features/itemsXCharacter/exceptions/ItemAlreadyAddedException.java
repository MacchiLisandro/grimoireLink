package com.maliag.grimoireLink.features.itemsXCharacter.exceptions;

import com.maliag.grimoireLink.common.exceptions.ConflictException;

public class ItemAlreadyAddedException extends ConflictException {
    public ItemAlreadyAddedException(String message) {
        super(message);
    }
}