package com.maliag.grimoireLink.features.encounter.exceptions;

import com.maliag.grimoireLink.common.exceptions.ConflictException;

public class CharacterNotInCampaignException extends ConflictException {
    public CharacterNotInCampaignException(String message) {
        super(message);
    }
}
