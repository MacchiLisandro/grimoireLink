package com.maliag.grimoireLink.features.characters.exceptions;

import com.maliag.grimoireLink.common.exceptions.ResourceNotFoundException;

public class CharacterNotFoundException extends ResourceNotFoundException {
    public CharacterNotFoundException(String message) {
        super(message);
    }
}
