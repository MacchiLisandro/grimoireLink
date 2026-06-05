package com.maliag.grimoireLink.features.encounter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncounterRepository extends JpaRepository<EncounterEntity, Long> {
}
