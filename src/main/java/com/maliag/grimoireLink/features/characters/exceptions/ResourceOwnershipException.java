package com.maliag.grimoireLink.features.characters.exceptions;

import com.maliag.grimoireLink.common.exceptions.ForbiddenException;

public class ResourceOwnershipException extends ForbiddenException {
    public ResourceOwnershipException(String message) {
        super(message);
    }
}