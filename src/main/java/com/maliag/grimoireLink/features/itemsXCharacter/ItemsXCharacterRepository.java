package com.maliag.grimoireLink.features.itemsXCharacter;

import com.maliag.grimoireLink.features.characters.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsXCharacterRepository extends JpaRepository<ItemsXCharacterEntity, Long> {
    List<ItemsXCharacterEntity> findByCharacter(CharacterEntity character);
}
