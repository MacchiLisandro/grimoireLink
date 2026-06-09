package com.maliag.grimoireLink.features.monsters;

import com.maliag.grimoireLink.features.dndapi.DnDApiService;
import com.maliag.grimoireLink.features.monsters.dtos.MonsterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MonsterServiceImpl implements MonsterService{
    private final MonsterRepository monsterRepository;
    private final MonsterMapper monsterMapper;
    private final DnDApiService dndApiService;

    public MonsterResponse createMonsterFromApi (String index){
        return monsterMapper.toResponse(
                monsterRepository.save(
                        monsterMapper.toEntity(
                                dndApiService.getMonsterByIndex(index))));
    }

    public MonsterResponse getByPublicId(UUID publicId) {
        return monsterMapper.toResponse(
                monsterRepository.findByPublicId(publicId)
                        .orElseThrow(() -> new MonsterNotFoundException("Monster not found")));
    }
}
