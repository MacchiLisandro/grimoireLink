package com.maliag.grimoireLink.features.users.repositories;

import com.maliag.grimoireLink.features.users.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPublicId(UUID id);
}
