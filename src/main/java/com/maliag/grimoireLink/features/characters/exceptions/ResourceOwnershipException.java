package com.maliag.grimoireLink.features.characters.exceptions;

import com.maliag.grimoireLink.common.exceptions.UnauthorizedException;

public class ResourceOwnershipException extends UnauthorizedException {
    public ResourceOwnershipException(String message) {
        super(message);
    }
}