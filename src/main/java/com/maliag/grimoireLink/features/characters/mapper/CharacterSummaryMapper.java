package com.maliag.grimoireLink.features.characters.mapper;

import com.maliag.grimoireLink.features.characters.dto.CharacterEncounterResponse;
import com.maliag.grimoireLink.features.characters.model.CharacterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CharacterSummaryMapper {
    CharacterEncounterResponse toCharacterEncounterResponse(CharacterEntity entity);
}