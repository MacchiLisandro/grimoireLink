package com.maliag.grimoireLink.features.characters;

import com.maliag.grimoireLink.features.characters.dto.CharacterEncounterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CharacterSummaryMapper {
    CharacterEncounterResponse toCharacterEncounterResponse(CharacterEntity entity);
}