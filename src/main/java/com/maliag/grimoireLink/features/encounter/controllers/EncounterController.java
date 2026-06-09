package com.maliag.grimoireLink.features.encounter.controllers;

import com.maliag.grimoireLink.features.encounter.services.EncounterService;
import com.maliag.grimoireLink.features.encounter.enums.EncounterStatus;
import com.maliag.grimoireLink.features.encounter.dto.EncounterRequest;
import com.maliag.grimoireLink.features.encounter.dto.EncounterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/encounters")
@RequiredArgsConstructor
@Validated
public class EncounterController {

    private final EncounterService encounterService;

    @PostMapping
    public ResponseEntity<EncounterResponse> saveEncounter(@RequestBody @Valid EncounterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(encounterService.saveEncounter(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EncounterResponse> getEncounterById(@PathVariable UUID id) {
        return ResponseEntity.ok(encounterService.getEncounterById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EncounterResponse> updateStatus(@PathVariable UUID id,
                                                          @RequestParam EncounterStatus status) {
        return ResponseEntity.ok(encounterService.updateStatusEncounter(id, status));
    }

    @PostMapping("/{id}/characters/{characterId}")
    public ResponseEntity<EncounterResponse> addCharacter(@PathVariable UUID id,
                                                          @PathVariable UUID characterId) {
        return ResponseEntity.ok(encounterService.addCharacter(id, characterId));
    }

    @PostMapping("/{id}/monsters/{monsterId}")
    public ResponseEntity<EncounterResponse> addMonster(@PathVariable UUID id,
                                                        @PathVariable String monsterId) {
        return ResponseEntity.ok(encounterService.addMonster(id, monsterId));
    }

    @PatchMapping("/{id}/characters/{characterId}/hp")
    public ResponseEntity<EncounterResponse> updateCharacterHp(@PathVariable UUID id,
                                                               @PathVariable UUID characterId,
                                                               @RequestParam int newHp) {
        return ResponseEntity.ok(encounterService.updateCharacterHp(id, characterId, newHp));
    }

    @PatchMapping("/{id}/monsters/{monsterId}/hp")
    public ResponseEntity<EncounterResponse> updateMonsterHp(@PathVariable UUID id,
                                                             @PathVariable UUID monsterId,
                                                             @RequestParam int newHp) {
        return ResponseEntity.ok(encounterService.updateMonsterHp(id, monsterId, newHp));
    }
}
