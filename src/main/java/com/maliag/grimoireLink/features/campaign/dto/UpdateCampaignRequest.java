package com.maliag.grimoireLink.features.campaign.dto;

import com.maliag.grimoireLink.features.campaign.enums.CampaignStatus;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCampaignRequest {

    private String name;

    @Size(max = 65535, message = "La descripción no puede tener más de 65535 caracteres")
    private String description;

    private CampaignStatus status;
}
