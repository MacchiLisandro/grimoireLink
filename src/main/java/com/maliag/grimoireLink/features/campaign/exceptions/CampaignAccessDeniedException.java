package com.maliag.grimoireLink.features.campaign.exceptions;

import com.maliag.grimoireLink.common.exceptions.ForbiddenException;

public class CampaignAccessDeniedException extends ForbiddenException {
    public CampaignAccessDeniedException(String message) {
        super(message);
    }
}