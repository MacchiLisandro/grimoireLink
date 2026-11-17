package com.maliag.grimoireLink.features.campaign.dto;

import com.maliag.grimoireLink.features.campaign.enums.CampaignStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CampaignResponse {
    private UUID publicId;
    private String name;
    private String description;
    private Long inviteCode;
    private CampaignStatus status;
}
