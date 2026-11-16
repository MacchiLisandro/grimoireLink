package com.maliag.grimoireLink.features.monsters.services;

import com.maliag.grimoireLink.features.monsters.dtos.MonsterReferenceResponse;
import com.maliag.grimoireLink.features.monsters.dtos.MonsterResponse;
import com.maliag.grimoireLink.features.monsters.models.MonsterEntity;

import java.util.List;
import java.util.UUID;

public interface MonsterService {
    MonsterEntity createMonsterFromApi (String index);
    MonsterResponse getMonsterFromApi(String index);
    List<MonsterReferenceResponse> getCatalog();
    MonsterResponse getByPublicId(UUID publicId);
    void updateHp(UUID monsterId, int newHp);
}
