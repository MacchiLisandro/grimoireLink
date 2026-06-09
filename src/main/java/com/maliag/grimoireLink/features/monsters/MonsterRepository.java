package com.maliag.grimoireLink.features.monsters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MonsterRepository extends JpaRepository<MonsterEntity, Long> {
    Optional<MonsterEntity> findByPublicId(UUID publicId);
}
