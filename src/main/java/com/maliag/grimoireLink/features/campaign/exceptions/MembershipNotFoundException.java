package com.maliag.grimoireLink.features.campaign.exceptions;

import com.maliag.grimoireLink.common.exceptions.ResourceNotFoundException;

public class MembershipNotFoundException extends ResourceNotFoundException {
    public MembershipNotFoundException(String message) {
        super(message);
    }
}
