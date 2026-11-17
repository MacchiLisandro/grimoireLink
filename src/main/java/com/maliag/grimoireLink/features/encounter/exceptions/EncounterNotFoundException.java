package com.maliag.grimoireLink.features.encounter.exceptions;

import com.maliag.grimoireLink.common.exceptions.ResourceNotFoundException;

public class EncounterNotFoundException extends ResourceNotFoundException {
    public EncounterNotFoundException(String message) {
        super(message);
    }
}
