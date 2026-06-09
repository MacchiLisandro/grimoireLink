package com.maliag.grimoireLink.features.monsters.mappers;

import com.maliag.grimoireLink.features.dndapi.dto.MonsterApiResponse;
import com.maliag.grimoireLink.features.monsters.dtos.MonsterResponse;
import com.maliag.grimoireLink.features.monsters.models.MonsterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MonsterMapper {

    @Mapping(target = "maxHp", source = "hitPoints")
    @Mapping(target = "currentHp", source = "hitPoints")
    @Mapping(target = "isAlive", constant = "true")
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "id", ignore = true)
    MonsterEntity toEntity(MonsterApiResponse response);

    MonsterResponse toResponse(MonsterEntity monster);
}
