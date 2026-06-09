package com.maliag.grimoireLink.features.encounter;

import com.maliag.grimoireLink.features.characters.CharacterService;
import com.maliag.grimoireLink.features.encounter.dto.EncounterRequest;
import com.maliag.grimoireLink.features.encounter.dto.EncounterResponse;
import com.maliag.grimoireLink.features.encounter.exceptions.EncounterNotFoundException;
import com.maliag.grimoireLink.features.monsters.MonsterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EncounterServiceImpl implements EncounterService {
    private final EncounterRepository encounterRepository;
    private final EncounterMapper encounterMapper;
    private final CharacterService characterService;
    private final MonsterService monsterService;

    @Transactional
    public EncounterResponse saveEncounter(EncounterRequest Request) {
        return encounterMapper.toResponse(encounterRepository.save(encounterMapper.toEntity(Request)));
    }


    @Transactional(readOnly = true)
    public EncounterResponse getEncounterById (UUID encounterId){
        return encounterMapper.toResponse(encounterRepository.findByPublicId(encounterId)
                        .orElseThrow(() -> new EncounterNotFoundException("Encounter not found")));
    }

    @Transactional
    public EncounterResponse updateStatusEncounter (UUID encounterId, EncounterStatus status){
        EncounterEntity encounter = encounterRepository.findByPublicId(encounterId)
                .orElseThrow(() -> new EncounterNotFoundException("Encounter not found"));
        encounter.setEncounterStatus(status);
        return encounterMapper.toResponse(encounter);
    }

    @Transactional
    public EncounterResponse addCharacter (UUID encounterId, UUID characterId){
        EncounterEntity encounter = encounterRepository.findByPublicId(encounterId)
                .orElseThrow(() -> new EncounterNotFoundException("Encounter not found"));
        encounter.getCharacters().add(characterService.getCharacterByPublicId(characterId));
        return encounterMapper.toResponse(encounter);
    }

    @Transactional
    public EncounterResponse addMonster (UUID encounterId, String monstruoId){
        EncounterEntity encounter = encounterRepository.findByPublicId(encounterId)
                .orElseThrow(() -> new EncounterNotFoundException("Encounter not found"));
        encounter.getMonsters().add(monsterService.createMonsterFromApi(monstruoId));
        return encounterMapper.toResponse(encounter);
    }

    @Transactional
    public EncounterResponse updateCharacterHp(UUID encounterId, UUID characterId, int newHp) {
        EncounterEntity encounter = encounterRepository.findByPublicId(encounterId)
                .orElseThrow(() -> new EncounterNotFoundException("Encounter not found"));
        encounter.getCharacters().stream()
                .filter(x -> x.getPublicId().equals(characterId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Character not in encounter"))
                .setCurrentHp(newHp);
        return encounterMapper.toResponse(encounter);
    }

    @Transactional
    public EncounterResponse updateMonsterHp(UUID encounterId, UUID monsterId, int newHp) {
        EncounterEntity encounter = encounterRepository.findByPublicId(encounterId)
                .orElseThrow(() -> new EncounterNotFoundException("Encounter not found"));
        encounter.getMonsters().stream()
                .filter(x -> x.getPublicId().equals(monsterId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Monster not in encounter"))
                .setCurrentHp(newHp);
        return encounterMapper.toResponse(encounter);
    }

}
