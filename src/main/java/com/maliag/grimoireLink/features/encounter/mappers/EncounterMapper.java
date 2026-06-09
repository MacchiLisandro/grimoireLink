package com.maliag.grimoireLink.features.encounter.mappers;

import com.maliag.grimoireLink.features.characters.CharacterSummaryMapper;
import com.maliag.grimoireLink.features.encounter.dto.EncounterRequest;
import com.maliag.grimoireLink.features.encounter.dto.EncounterResponse;
import com.maliag.grimoireLink.features.encounter.models.EncounterEntity;
import com.maliag.grimoireLink.features.monsters.mappers.MonsterMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CharacterSummaryMapper.class, MonsterMapper.class})
public interface EncounterMapper {
    EncounterResponse toResponse(EncounterEntity encounter);
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "characters", ignore = true)
    @Mapping(target = "monsters", ignore = true)
    @Mapping(target = "encounterStatus", ignore = true)
    EncounterEntity toEntity(EncounterRequest encounter);
}
