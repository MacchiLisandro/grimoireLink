package com.maliag.grimoireLink.features.encounter.dto;

import com.maliag.grimoireLink.features.characters.dto.CharacterEncounterResponse;
import com.maliag.grimoireLink.features.characters.dto.CharacterResponse;
import com.maliag.grimoireLink.features.encounter.enums.EncounterDifficulty;
import com.maliag.grimoireLink.features.encounter.enums.EncounterStatus;
import com.maliag.grimoireLink.features.encounter.enums.EncounterType;
import com.maliag.grimoireLink.features.monsters.dtos.MonsterResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EncounterResponse {

    private UUID publicId;
    private EncounterType encounterType;
    private EncounterDifficulty encounterDifficulty;
    private String description;
    private EncounterStatus encounterStatus;
    private List<MonsterResponse> monsters;
    private List<CharacterEncounterResponse> characters;
}
