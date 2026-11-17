package com.maliag.grimoireLink.features.characters.exceptions;

import com.maliag.grimoireLink.common.exceptions.ForbiddenException;

public class CharacterAccessDeniedException extends ForbiddenException {
    public CharacterAccessDeniedException(String message) {
        super(message);
    }
}