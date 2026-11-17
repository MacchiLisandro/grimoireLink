package com.maliag.grimoireLink.features.journal.repository;

import com.maliag.grimoireLink.features.journal.model.JournalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JournalRepository extends JpaRepository<JournalEntity, Long>,
        JpaSpecificationExecutor<JournalEntity> {

    Optional<JournalEntity> findByPublicId(UUID publicId);
}
