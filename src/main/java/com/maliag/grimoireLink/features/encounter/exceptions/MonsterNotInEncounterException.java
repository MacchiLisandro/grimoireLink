package com.maliag.grimoireLink.features.encounter.exceptions;

import com.maliag.grimoireLink.common.exceptions.ResourceNotFoundException;

public class MonsterNotInEncounterException extends ResourceNotFoundException {
    public MonsterNotInEncounterException(String message) {
        super(message);
    }
}
