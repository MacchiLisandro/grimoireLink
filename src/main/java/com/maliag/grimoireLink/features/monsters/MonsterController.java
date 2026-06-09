package com.maliag.grimoireLink.features.monsters;

import com.maliag.grimoireLink.features.monsters.dtos.MonsterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/monster")
@RequiredArgsConstructor
@Validated
public class MonsterController {
    private final MonsterService monsterService;

    @GetMapping("/api/{id}")
    public ResponseEntity<MonsterResponse> getMonsterFromApi(@PathVariable String id){
        return ResponseEntity.ok().body(monsterService.getMonsterFromApi(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonsterResponse> getMonsterById (@PathVariable UUID id){
        return ResponseEntity.ok().body(monsterService.getByPublicId(id));
    }
}
