package com.maliag.grimoireLink.features.encounter.dto;

import com.maliag.grimoireLink.features.encounter.enums.EncounterDifficulty;
import com.maliag.grimoireLink.features.encounter.enums.EncounterType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EncounterRequest {
    @NotNull
    private UUID campaignId;

    @NotNull
    private EncounterType encounterType;

    @NotNull
    private EncounterDifficulty encounterDifficulty;

    @Size(max = 65535)
    private String description;

}
