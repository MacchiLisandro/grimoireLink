package com.maliag.grimoireLink.features.encounter;

import com.maliag.grimoireLink.features.characters.CharacterSummaryMapper;
import com.maliag.grimoireLink.features.encounter.dto.EncounterRequest;
import com.maliag.grimoireLink.features.encounter.dto.EncounterResponse;
import com.maliag.grimoireLink.features.monsters.MonsterMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CharacterSummaryMapper.class, MonsterMapper.class})
public interface EncounterMapper {
    EncounterResponse toResponse(EncounterEntity encounter);
    EncounterEntity toEntity(EncounterRequest encounter);
}
