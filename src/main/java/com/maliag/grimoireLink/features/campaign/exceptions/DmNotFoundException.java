package com.maliag.grimoireLink.features.campaign.exceptions;

import com.maliag.grimoireLink.common.exceptions.ResourceNotFoundException;

public class DmNotFoundException extends ResourceNotFoundException {
    public DmNotFoundException(String message) {
        super(message);
    }
}
