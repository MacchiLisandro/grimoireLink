package com.maliag.grimoireLink.features.monsters.services;

import com.maliag.grimoireLink.features.dndapi.DnDApiService;
import com.maliag.grimoireLink.features.dndapi.dto.DndReference;
import com.maliag.grimoireLink.features.monsters.dtos.MonsterReferenceResponse;
import com.maliag.grimoireLink.features.monsters.mappers.MonsterMapper;
import com.maliag.grimoireLink.features.monsters.repositories.MonsterRepository;
import com.maliag.grimoireLink.features.monsters.dtos.MonsterResponse;
import com.maliag.grimoireLink.features.monsters.exceptions.MonsterNotFoundException;
import com.maliag.grimoireLink.features.monsters.models.MonsterEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MonsterServiceImpl implements MonsterService {
    private final MonsterRepository monsterRepository;
    private final MonsterMapper monsterMapper;
    private final DnDApiService dndApiService;

    @Transactional
    public MonsterEntity createMonsterFromApi (String index){
        MonsterEntity monster = monsterMapper.toEntity(dndApiService.getMonsterByIndex(index));
        monster.setCurrentHp(monster.getMaxHp());
        return monsterRepository.save(monster);
    }

    @Transactional
    public MonsterResponse getMonsterFromApi (String index){
        return monsterMapper.toResponse(monsterMapper.toEntity(dndApiService.getMonsterByIndex(index)));
    }

    @Override
    public List<MonsterReferenceResponse> getCatalog() {
        return dndApiService.getMonsters().stream()
                .map(monsterMapper::toReferenceResponse)
                .toList();
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

        int hp = Math.max(0, Math.min(newHp, monster.getMaxHp()));

        monster.setCurrentHp(hp);
        monster.setIsAlive(hp > 0);
    }
}
