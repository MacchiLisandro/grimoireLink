package com.maliag.grimoireLink.features.encounter.services;

import com.maliag.grimoireLink.features.campaign.model.CampaignEntity;
import com.maliag.grimoireLink.features.campaign.service.CampaignService;
import com.maliag.grimoireLink.features.characters.CharacterService;
import com.maliag.grimoireLink.features.encounter.exceptions.MonsterNotInEncounterException;
import com.maliag.grimoireLink.features.encounter.models.EncounterEntity;
import com.maliag.grimoireLink.features.encounter.mappers.EncounterMapper;
import com.maliag.grimoireLink.features.encounter.repositories.EncounterRepository;
import com.maliag.grimoireLink.features.encounter.dto.EncounterRequest;
import com.maliag.grimoireLink.features.encounter.dto.EncounterResponse;
import com.maliag.grimoireLink.features.encounter.enums.EncounterStatus;
import com.maliag.grimoireLink.features.encounter.exceptions.CharacterAlreadyInEncounterException;
import com.maliag.grimoireLink.features.encounter.exceptions.EncounterNotFoundException;
import com.maliag.grimoireLink.features.monsters.services.MonsterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EncounterServiceImpl implements EncounterService {
    private final EncounterRepository encounterRepository;
    private final EncounterMapper encounterMapper;
    private final CharacterService characterService;
    private final MonsterService monsterService;
    private final CampaignService campaignService;

    @Transactional
    public EncounterResponse saveEncounter(EncounterRequest request) {
        CampaignEntity campaign = campaignService.findByPublicId(request.getCampaignId());
        EncounterEntity encounter = encounterMapper.toEntity(request);
        encounter.setEncounterStatus(EncounterStatus.PLANNED);
        encounter.setCampaign(campaign);
        return encounterMapper.toResponse(encounterRepository.save(encounter));
    }


    @Transactional(readOnly = true)
    public EncounterResponse getEncounterById (UUID encounterId){
        return encounterMapper.toResponse(encounterRepository.findByPublicId(encounterId)
                        .orElseThrow(() -> new EncounterNotFoundException("Encounter not found")));
    }

    @Transactional(readOnly = true)
    public List<EncounterResponse> getAllEncountersByCampaign(UUID publicId) {
        return encounterRepository.findByCampaignPublicId(publicId).stream()
                .map(encounterMapper::toResponse)
                .toList();
    }

    @Transactional
    public EncounterResponse updateStatusEncounter (UUID encounterId, EncounterStatus status){
        EncounterEntity encounter = encounterRepository.findByPublicId(encounterId)
                .orElseThrow(() -> new EncounterNotFoundException("Encounter not found"));
        encounter.setEncounterStatus(status);
        return encounterMapper.toResponse(encounter);
    }

    @Transactional
    public EncounterResponse addCharacter(UUID encounterId, UUID characterId) {
        EncounterEntity encounter = encounterRepository.findByPublicId(encounterId)
                .orElseThrow(() -> new EncounterNotFoundException("Encounter not found"));
        boolean alreadyInEncounter = encounter.getCharacters().stream()
                .anyMatch(c -> c.getPublicId().equals(characterId));
        if (alreadyInEncounter) {
            throw new CharacterAlreadyInEncounterException("Character already in encounter");
        }
        encounter.getCharacters().add(characterService.findCharacterByPublicId(characterId));
        return encounterMapper.toResponse(encounter);
    }

    @Transactional
    public EncounterResponse addMonster(UUID encounterId, String monsterId) {
        EncounterEntity encounter = encounterRepository.findByPublicId(encounterId)
                .orElseThrow(() -> new EncounterNotFoundException("Encounter not found"));
        encounter.getMonsters().add(monsterService.createMonsterFromApi(monsterId));
        return encounterMapper.toResponse(encounter);
    }

    @Transactional
    public EncounterResponse updateCharacterHp(UUID encounterId, UUID characterId, int newHp) {
        EncounterEntity encounter = encounterRepository.findByPublicId(encounterId)
                .orElseThrow(() -> new EncounterNotFoundException("Encounter not found"));
        encounter.getCharacters().stream()
                .filter(x -> x.getPublicId().equals(characterId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Character not in encounter"));
        characterService.updateHp(characterId, newHp);
        return encounterMapper.toResponse(encounter);
    }

    @Transactional
    public EncounterResponse updateMonsterHp(UUID encounterId, UUID monsterId, int newHp) {
        EncounterEntity encounter = encounterRepository.findByPublicId(encounterId)
                .orElseThrow(() -> new EncounterNotFoundException("Encounter not found"));
        encounter.getMonsters().stream()
                .filter(x -> x.getPublicId().equals(monsterId))
                .findFirst()
                .orElseThrow(() -> new MonsterNotInEncounterException("Monster not in encounter"));
        monsterService.updateHp(monsterId, newHp);
        return encounterMapper.toResponse(encounter);
    }

    @Transactional
    public EncounterResponse removeCharacter(UUID encounterId, UUID characterId) {
        EncounterEntity encounter = encounterRepository.findByPublicId(encounterId)
                .orElseThrow(() -> new EncounterNotFoundException("Encounter not found"));
        encounter.getCharacters().removeIf(c -> c.getPublicId().equals(characterId));
        return encounterMapper.toResponse(encounter);
    }

    @Transactional
    public EncounterResponse removeMonster(UUID encounterId, UUID monsterId) {
        EncounterEntity encounter = encounterRepository.findByPublicId(encounterId)
                .orElseThrow(() -> new EncounterNotFoundException("Encounter not found"));
        encounter.getMonsters().removeIf(m -> m.getPublicId().equals(monsterId));
        return encounterMapper.toResponse(encounter);
    }
}
