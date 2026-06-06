package com.maliag.grimoireLink.features.skillsXCharacter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsXCharacterRepository extends JpaRepository<SkillsXCharacterEntity, Long> {
}
