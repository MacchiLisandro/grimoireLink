package com.maliag.grimoireLink.features.logCombat.CombatActionMapper;

import com.maliag.grimoireLink.features.encounter.models.EncounterEntity;
import com.maliag.grimoireLink.features.logCombat.CombatActionEntity;
import com.maliag.grimoireLink.features.logCombat.dto.CombatActionResponse;
import com.maliag.grimoireLink.features.logCombat.dto.DamageRequest;
import com.maliag.grimoireLink.features.logCombat.enums.CombatantType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component

public class Mapper {

    public CombatActionEntity toEntity(EncounterEntity encounter,
                                       DamageRequest request,
                                       CombatantType targetType,
                                       UUID targetId) {
        return CombatActionEntity.builder()
                .encounter(encounter)
                .actorType(request.getActorType())
                .actorId(request.getActorId())
                .targetType(targetType)
                .targetId(targetId)
                .actionType(request.getActionType())
                .damageType(request.getDamageType())
                .amount(request.getAmount())
                .build();
    }

    public CombatActionResponse toResponse(CombatActionEntity entity) {
        return CombatActionResponse.builder()
                .publicId(entity.getPublicId())
                .actorType(entity.getActorType())
                .actorId(entity.getActorId())
                .targetType(entity.getTargetType())
                .targetId(entity.getTargetId())
                .actionType(entity.getActionType())
                .damageType(entity.getDamageType())
                .amount(entity.getAmount())
                .createdAt(entity.getCreatedAt())
                .build();
    }


}
