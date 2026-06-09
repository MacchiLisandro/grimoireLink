package com.maliag.grimoireLink.features.encounter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EncounterRepository extends JpaRepository<EncounterEntity, Long> {
    Optional<EncounterEntity> findByPublicId (UUID publicId);
}
