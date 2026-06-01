package com.maliag.grimoireLink.features.featuresXCharacter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureXCharacterRepository extends JpaRepository<FeatureXCharacterEntity, Long> {
}
