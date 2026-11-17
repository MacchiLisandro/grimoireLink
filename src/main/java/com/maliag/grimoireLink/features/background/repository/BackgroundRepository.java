package com.maliag.grimoireLink.features.background.repository;

import com.maliag.grimoireLink.features.background.model.BackgroundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BackgroundRepository extends JpaRepository<BackgroundEntity, Long> {


    Optional<BackgroundEntity> findBybackgroundIndex(String index);
}
