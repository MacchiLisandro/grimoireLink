package com.maliag.grimoireLink.features.dndapi.exceptions;

import com.maliag.grimoireLink.common.exceptions.ResourceNotFoundException;

public class ClassNotFoundException extends ResourceNotFoundException {
    public ClassNotFoundException(String message) {
        super(message);
    }
}
