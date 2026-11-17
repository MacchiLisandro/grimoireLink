package com.maliag.grimoireLink.features.campaign.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignRequest {

    @NotBlank(message = "Se debe incluir el nombre de la campaña")
    private String name;

    @NotBlank(message = "Se debe incluir la descripción de la campaña")
    @Size(max = 65535, message = "La descripción no puede tener más de 65535 caracteres")
    private String description;
}
