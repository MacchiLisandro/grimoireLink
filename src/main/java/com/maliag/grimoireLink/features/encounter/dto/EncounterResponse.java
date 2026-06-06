package com.maliag.grimoireLink.features.encounter.dto;

import com.maliag.grimoireLink.features.encounter.ChallengeRating;
import com.maliag.grimoireLink.features.encounter.EncounterStatus;
import com.maliag.grimoireLink.features.encounter.EncounterType;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EncounterResponse {

    private UUID publicId;
    private EncounterType encounterType;
    private ChallengeRating challengeRating;
    private String description;
    private EncounterStatus encounterStatus;
}
