package com.maliag.grimoireLink.features.skillsXCharacter;

import com.maliag.grimoireLink.features.characters.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillsXCharacterRepository extends JpaRepository<SkillsXCharacterEntity, Long> {
    List<SkillsXCharacterEntity> findByCharacter(CharacterEntity character);
}
