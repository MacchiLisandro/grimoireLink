package com.maliag.grimoireLink.features.monsters.services;

import com.maliag.grimoireLink.features.dndapi.DnDApiService;
import com.maliag.grimoireLink.features.monsters.mappers.MonsterMapper;
import com.maliag.grimoireLink.features.monsters.repositories.MonsterRepository;
import com.maliag.grimoireLink.features.monsters.dtos.MonsterResponse;
import com.maliag.grimoireLink.features.monsters.exceptions.MonsterNotFoundException;
import com.maliag.grimoireLink.features.monsters.models.MonsterEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MonsterServiceImpl implements MonsterService {
    private final MonsterRepository monsterRepository;
    private final MonsterMapper monsterMapper;
    private final DnDApiService dndApiService;

    @Transactional
    public MonsterEntity createMonsterFromApi (String index){
        return monsterRepository.save(monsterMapper.toEntity(dndApiService.getMonsterByIndex(index)));
    }

    @Transactional
    public MonsterResponse getMonsterFromApi (String index){
        return monsterMapper.toResponse(monsterMapper.toEntity(dndApiService.getMonsterByIndex(index)));
    }

    public MonsterResponse getByPublicId(UUID publicId) {
        return monsterMapper.toResponse(
                monsterRepository.findByPublicId(publicId)
                        .orElseThrow(() -> new MonsterNotFoundException("Monster not found")));
    }

    @Transactional
    public void updateHp(UUID monsterId, int newHp) {
        MonsterEntity monster = monsterRepository.findByPublicId(monsterId)
                .orElseThrow(() -> new MonsterNotFoundException("Monster not found"));
        if (newHp <= 0) {
            monster.setCurrentHp(0);
            monster.setIsAlive(false);
        } else {
            monster.setCurrentHp(newHp);
        }
    }
}
