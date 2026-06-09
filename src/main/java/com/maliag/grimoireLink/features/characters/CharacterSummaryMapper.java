package com.maliag.grimoireLink.features.characters;

import com.maliag.grimoireLink.features.characters.dto.CharacterEncounterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CharacterSummaryMapper {
    CharacterEncounterResponse toCharacterEncounterResponse (CharacterEntity entity);
}
