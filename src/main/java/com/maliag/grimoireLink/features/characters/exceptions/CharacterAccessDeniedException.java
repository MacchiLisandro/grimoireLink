package com.maliag.grimoireLink.features.characters.exceptions;

import com.maliag.grimoireLink.common.exceptions.UnauthorizedException;

public class CharacterAccessDeniedException extends UnauthorizedException {
    public CharacterAccessDeniedException(String message) {
        super(message);
    }
}