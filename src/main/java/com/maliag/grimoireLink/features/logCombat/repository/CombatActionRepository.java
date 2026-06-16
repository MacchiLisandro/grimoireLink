package com.maliag.grimoireLink.features.logCombat.repository;

import com.maliag.grimoireLink.features.logCombat.CombatActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CombatActionRepository extends JpaRepository<CombatActionEntity, Long> {

    List<CombatActionEntity> findByEncounterPublicIdOrderByCreatedAtAsc(UUID encounterPublicId);

}
