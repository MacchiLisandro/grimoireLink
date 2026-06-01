package com.maliag.grimoireLink.features.spellsXCharacter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpellsXCharacterRepository extends JpaRepository<SpellsXCharacterEntity, Long> {
}
