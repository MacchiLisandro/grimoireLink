package com.maliag.grimoireLink.features.featuresXCharacter;

import com.maliag.grimoireLink.features.characters.model.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureXCharacterRepository extends JpaRepository<FeatureXCharacterEntity, Long> {

    List<FeatureXCharacterEntity>findByCharacter(CharacterEntity character);
}
