package com.maliag.grimoireLink.features.campaign.exceptions;

import com.maliag.grimoireLink.common.exceptions.ConflictException;

public class AlreadyMemberException extends ConflictException {
    public AlreadyMemberException(String message) {
        super(message);
    }
}
