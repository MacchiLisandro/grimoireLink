package com.maliag.grimoireLink.features.encounter.dto;

import com.maliag.grimoireLink.features.encounter.enums.EncounterDifficulty;
import com.maliag.grimoireLink.features.encounter.enums.EncounterType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EncounterRequest {

    @NotNull
    private EncounterType encounterType;

    @NotNull
    private EncounterDifficulty encounterDifficulty;

    @Size(max = 65535)
    private String description;

}
