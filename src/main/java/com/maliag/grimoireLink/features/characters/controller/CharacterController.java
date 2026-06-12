package com.maliag.grimoireLink.features.characters.controller;

import com.maliag.grimoireLink.features.characters.service.CharacterService;
import com.maliag.grimoireLink.features.characters.dto.CharacterCreateRequest;
import com.maliag.grimoireLink.features.characters.dto.CharacterResponse;
import com.maliag.grimoireLink.features.characters.dto.CharacterUpdateRequest;
import com.maliag.grimoireLink.features.characters.dto.LevelUpRequest;
import com.maliag.grimoireLink.features.itemsXCharacter.dto.AddItemRequest;
import com.maliag.grimoireLink.features.spellsXCharacter.dto.AddSpellRequest;
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


/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    public CharacterResponse getCharacterById(@PathVariable UUID characterId,
                                              @RequestParam UUID campaignPublicId){
        return characterService.getCharacterByPublicId(characterId, campaignPublicId);
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
/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @PatchMapping("/{characterId}/levelup")
    public CharacterResponse levelUp(@PathVariable UUID characterId,
                                     @Valid @RequestBody LevelUpRequest request){

    return characterService.levelUp(characterId,request);
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

    @PostMapping("/{characterId}/spell")
    @ResponseStatus(HttpStatus.CREATED)
    public CharacterResponse addSpell(@PathVariable UUID characterId,
                                      @Valid @RequestBody AddSpellRequest request){
    return  characterService.addSpell(characterId,request);
    }

    /// devuelvo o no?
    @DeleteMapping("/{characterId}/spell/{spellId}")
    @ResponseStatus(HttpStatus.OK)
    public CharacterResponse removeSpell(@PathVariable UUID characterId,
                                         @PathVariable UUID spellId){

        return characterService.removeSpell(characterId,spellId);
    }

    @PatchMapping("/{characterId}/spells/{spellId}/prepare")
    public CharacterResponse togglePreparedSpell(@PathVariable UUID characterId,
                                                 @PathVariable UUID spellId){
        return characterService.togglePreparedSpell(characterId, spellId);
    }

    @PostMapping("/{characterId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CharacterResponse addItem(@PathVariable UUID characterId,
                                     @Valid @RequestBody AddItemRequest request){
        return characterService.addItem(characterId, request);
    }

    @DeleteMapping("/{characterId}/items/{itemId}")
    public CharacterResponse removeItem(@PathVariable UUID characterId,
                                        @PathVariable UUID itemId){
        return characterService.removeItem(characterId, itemId);
    }

    @PatchMapping("/{characterId}/items/{itemId}/equip")
    public CharacterResponse toggleEquippedItem(@PathVariable UUID characterId,
                                                @PathVariable UUID itemId){
        return characterService.toggleEquippedItem(characterId, itemId);
    }




}
