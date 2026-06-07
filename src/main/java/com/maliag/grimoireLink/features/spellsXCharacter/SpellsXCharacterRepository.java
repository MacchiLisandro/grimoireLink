package com.maliag.grimoireLink.features.spellsXCharacter;

import com.maliag.grimoireLink.features.characters.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpellsXCharacterRepository extends JpaRepository<SpellsXCharacterEntity, Long> {
    List<SpellsXCharacterEntity>findByCharacter(CharacterEntity character);

}
