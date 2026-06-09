package com.maliag.grimoireLink.features.encounter.exceptions;

import com.maliag.grimoireLink.common.exceptions.ConflictException;

public class CharacterAlreadyInEncounterException extends ConflictException {
    public CharacterAlreadyInEncounterException(String message) {
        super(message);
    }
}
