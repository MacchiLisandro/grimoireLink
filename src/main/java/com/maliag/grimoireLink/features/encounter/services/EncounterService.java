package com.maliag.grimoireLink.features.encounter.services;

import com.maliag.grimoireLink.features.encounter.dto.EncounterRequest;
import com.maliag.grimoireLink.features.encounter.dto.EncounterResponse;
import com.maliag.grimoireLink.features.encounter.enums.EncounterStatus;

import java.util.UUID;

public interface EncounterService {
    EncounterResponse saveEncounter(EncounterRequest request);
    EncounterResponse getEncounterById(UUID encounterId);
    EncounterResponse updateStatusEncounter(UUID encounterId, EncounterStatus status);
    EncounterResponse addCharacter(UUID encounterId, UUID characterId);
    EncounterResponse addMonster(UUID encounterId, String monsterId);
    EncounterResponse updateCharacterHp(UUID encounterId, UUID characterId, int newHp);
    EncounterResponse updateMonsterHp(UUID encounterId, UUID monsterId, int newHp);
}
