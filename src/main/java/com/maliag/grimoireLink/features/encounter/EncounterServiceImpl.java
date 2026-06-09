package com.maliag.grimoireLink.features.encounter;

import com.maliag.grimoireLink.features.characters.CharacterService;
import com.maliag.grimoireLink.features.encounter.dto.EncounterResponse;
import com.maliag.grimoireLink.features.encounter.exceptions.EncounterNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EncounterServiceImpl {
    private final EncounterRepository encounterRepository;
    private final EncounterMapper encounterMapper;
    private final CharacterService characterService;

    @Transactional
    public EncounterResponse addCharacter (UUID encounterId, UUID characterId){
        EncounterEntity encounter = encounterRepository.findByPublicId(encounterId)
                .orElseThrow(() -> new EncounterNotFoundException("Encounter not found"));
        encounter.getCharacters().add(characterService.getCharacterByPublicId(characterId));
    }

}
