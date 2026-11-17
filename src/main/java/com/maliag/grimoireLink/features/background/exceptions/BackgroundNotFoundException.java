package com.maliag.grimoireLink.features.background.exceptions;

import com.maliag.grimoireLink.common.exceptions.ResourceNotFoundException;

public class BackgroundNotFoundException extends ResourceNotFoundException {
    public BackgroundNotFoundException(String message) {
        super(message);
    }
}