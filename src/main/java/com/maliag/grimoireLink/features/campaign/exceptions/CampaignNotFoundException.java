package com.maliag.grimoireLink.features.campaign.exceptions;

import com.maliag.grimoireLink.common.exceptions.ResourceNotFoundException;

public class CampaignNotFoundException extends ResourceNotFoundException {
    public CampaignNotFoundException(String message) {
        super(message);
    }
}
