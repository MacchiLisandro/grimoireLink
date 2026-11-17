package com.maliag.grimoireLink.features.dndapi.exceptions;

import com.maliag.grimoireLink.common.exceptions.ResourceNotFoundException;

public class RaceNotFoundException extends ResourceNotFoundException {
    public RaceNotFoundException(String message) {
        super(message);
    }
}
