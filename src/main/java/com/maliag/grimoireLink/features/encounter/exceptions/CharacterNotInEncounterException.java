package com.maliag.grimoireLink.features.encounter.exceptions;

import com.maliag.grimoireLink.common.exceptions.ConflictException;

public class CharacterNotInEncounterException extends ConflictException {
    public CharacterNotInEncounterException(String message) {
        super(message);
    }
}
