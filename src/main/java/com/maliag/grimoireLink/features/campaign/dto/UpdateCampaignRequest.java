package com.maliag.grimoireLink.features.campaign.dto;

import com.maliag.grimoireLink.features.campaign.CampaignStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCampaignRequest {

    private String name;

    @Size(max = 65535)
    private String description;

    private CampaignStatus status;
}
