package com.maliag.grimoireLink.features.encounter.dto;

import com.maliag.grimoireLink.features.encounter.ChallengeRating;
import com.maliag.grimoireLink.features.encounter.EncounterStatus;
import com.maliag.grimoireLink.features.encounter.EncounterType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EncounterRequest {

    @NotNull
    private EncounterType encounterType;

    @NotNull
    private ChallengeRating challengeRating;

    @Size(max = 65535)
    private String description;

    @NotNull
    private EncounterStatus encounterStatus;
}
