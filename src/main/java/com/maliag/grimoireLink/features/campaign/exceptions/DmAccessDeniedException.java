package com.maliag.grimoireLink.features.campaign.exceptions;

import com.maliag.grimoireLink.common.exceptions.ForbiddenException;

public class DmAccessDeniedException extends ForbiddenException {
    public DmAccessDeniedException(String message) {
        super(message);
    }
}
