package com.maliag.grimoireLink.features.encounter.exceptions;

public class EncounterNotFoundException extends RuntimeException {
    public EncounterNotFoundException(String message) {
        super(message);
    }
}
