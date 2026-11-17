package com.maliag.grimoireLink.features.spellsXCharacter.exceptions;

import com.maliag.grimoireLink.common.exceptions.ResourceNotFoundException;

public class SpellNotFoundException extends ResourceNotFoundException {
    public SpellNotFoundException(String message) {
        super(message);
    }
}
