package com.maliag.grimoireLink.features.monsters.services;

import com.maliag.grimoireLink.features.monsters.dtos.MonsterResponse;
import com.maliag.grimoireLink.features.monsters.models.MonsterEntity;

import java.util.UUID;

public interface MonsterService {
    MonsterEntity createMonsterFromApi (String index);
    MonsterResponse getMonsterFromApi(String index);
    MonsterResponse getByPublicId(UUID publicId);
    void updateHp(UUID monsterId, int newHp);
}
