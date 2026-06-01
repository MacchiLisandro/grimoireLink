package com.maliag.grimoireLink.features.itemsXCharacter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsXCharacterRepository extends JpaRepository<ItemsXCharacterEntity, Long> {
}
