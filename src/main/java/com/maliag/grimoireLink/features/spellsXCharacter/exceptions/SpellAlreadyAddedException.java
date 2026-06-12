package com.maliag.grimoireLink.features.spellsXCharacter.exceptions;

import com.maliag.grimoireLink.common.exceptions.ConflictException;

public class SpellAlreadyAddedException extends ConflictException {
    public SpellAlreadyAddedException(String message) {
        super(message);
    }
}