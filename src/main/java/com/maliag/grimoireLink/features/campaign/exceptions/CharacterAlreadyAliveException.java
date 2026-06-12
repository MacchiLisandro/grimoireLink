package com.maliag.grimoireLink.features.campaign.exceptions;

import com.maliag.grimoireLink.common.exceptions.ConflictException;

public class CharacterAlreadyAliveException extends ConflictException {
    public CharacterAlreadyAliveException(String message) {
        super(message);
    }
}