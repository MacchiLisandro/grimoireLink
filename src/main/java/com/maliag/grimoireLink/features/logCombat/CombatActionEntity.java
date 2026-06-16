package com.maliag.grimoireLink.features.logCombat;

import com.maliag.grimoireLink.features.encounter.models.EncounterEntity;
import com.maliag.grimoireLink.features.logCombat.enums.ActionType;
import com.maliag.grimoireLink.features.logCombat.enums.CombatantType;
import com.maliag.grimoireLink.features.logCombat.enums.DamageType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "combat_actions")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CombatActionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "publicId", nullable = false,unique = true,updatable = false)
    private UUID publicId;

    @ManyToOne
    @JoinColumn(name = "encounter_id", nullable = false)
    private EncounterEntity encounter;

    @Enumerated(EnumType.STRING)
    @Column(name = "actor_type")
    private CombatantType actorType;

    @Column(name = "actor_id")
    private UUID actorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private CombatantType targetType;

    @Column(name = "target_id", nullable = false)
    private UUID targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    private ActionType actionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "damage_type")
    private DamageType damageType;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        if (publicId == null) {
            this.publicId = UUID.randomUUID();
        }
        this.createdAt = Instant.now();
    }



}
