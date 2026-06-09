package com.maliag.grimoireLink.features.characters.controller;

import com.maliag.grimoireLink.features.characters.CharacterService;
import com.maliag.grimoireLink.features.characters.dto.CharacterCreateRequest;
import com.maliag.grimoireLink.features.characters.dto.CharacterResponse;
import com.maliag.grimoireLink.features.characters.dto.CharacterUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
@Validated
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CharacterResponse createCharacter(@Valid @RequestBody CharacterCreateRequest request,
                                             @RequestParam UUID campaignId){
        return characterService.createCharacter(request, campaignId);
    }

    @GetMapping
    public List<CharacterResponse> getCharactersByCampaign(@RequestParam UUID campaignId){
        return characterService.getCharacterByCampaing(campaignId);
    }

    @GetMapping("/{characterId}")
    public CharacterResponse getCharacterById(@PathVariable UUID characterId){
        return characterService.getCharacterById(characterId);
    }

    @PatchMapping("/{characterId}")
    public CharacterResponse updateCharacter(@PathVariable UUID characterId,
                                             @Valid @RequestBody CharacterUpdateRequest request){
        return characterService.updateCharacter(characterId, request);
    }

    @DeleteMapping("/{characterId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharacter(@PathVariable UUID characterId){
        characterService.deleteCharacter(characterId);
    }

    @PatchMapping("/{characterId}/hp")
    public CharacterResponse updateHp(@PathVariable UUID characterId,
                                      @RequestParam int newHp){
        return characterService.updateHp(characterId, newHp);
    }

    @PatchMapping("/{characterId}/gold")
    public CharacterResponse updateGold(@PathVariable UUID characterId,
                                        @RequestParam int newGold){
        return characterService.updateGold(characterId, newGold);
    }


}
