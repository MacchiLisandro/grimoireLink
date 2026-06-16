package com.maliag.grimoireLink.features.logCombat.dto;

import com.maliag.grimoireLink.features.logCombat.enums.ActionType;
import com.maliag.grimoireLink.features.logCombat.enums.CombatantType;
import com.maliag.grimoireLink.features.logCombat.enums.DamageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CombatActionResponse {

    private UUID publicId;
    private CombatantType actorType;
    private UUID actorId;
    private CombatantType targetType;
    private UUID targetId;
    private ActionType actionType;
    private DamageType damageType;
    private int amount;
    private Instant createdAt;
}
