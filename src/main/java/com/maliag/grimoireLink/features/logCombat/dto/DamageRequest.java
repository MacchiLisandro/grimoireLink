package com.maliag.grimoireLink.features.logCombat.dto;

import com.maliag.grimoireLink.features.logCombat.enums.ActionType;
import com.maliag.grimoireLink.features.logCombat.enums.CombatantType;
import com.maliag.grimoireLink.features.logCombat.enums.DamageType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DamageRequest {

    @Positive
    private int amount;

    @NotNull
    private ActionType actionType;

    private CombatantType actorType;

    private UUID actorId;

    private DamageType damageType;


}
