package com.maliag.grimoireLink.features.campaign.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampaignRequestDto {
    private String name;
    private String description;
}
