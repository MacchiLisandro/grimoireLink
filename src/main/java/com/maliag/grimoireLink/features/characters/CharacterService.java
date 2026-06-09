package com.maliag.grimoireLink.features.characters;


import com.maliag.grimoireLink.features.characters.dto.CharacterCreateRequest;
import com.maliag.grimoireLink.features.characters.dto.CharacterResponse;
import com.maliag.grimoireLink.features.characters.dto.CharacterUpdateRequest;
import com.maliag.grimoireLink.features.itemsXCharacter.dto.AddItemRequest;
import com.maliag.grimoireLink.features.spellsXCharacter.dto.AddSpellRequest;

import java.util.List;
import java.util.UUID;

public interface CharacterService {

    CharacterResponse createCharacter(CharacterCreateRequest request,UUID campaingPublicID);

    CharacterResponse getCharacterById(UUID CharacterPublicId);

    List<CharacterResponse> getCharacterByCampaing(UUID campaignPublicId);

    CharacterResponse updateCharacter(UUID characterPublicId, CharacterUpdateRequest request);

    void deleteCharacter(UUID characterPublicId);

    CharacterResponse updateHp(UUID characterPublicId, int newHp);


    CharacterResponse updateGold(UUID characterPublicId, int newGold);


    CharacterResponse addSpell(UUID characterPublicId, AddSpellRequest request);
    CharacterResponse removeSpell(UUID characterPublicId, UUID spellPublicId);
    CharacterResponse togglePreparedSpell(UUID characterPublicId, UUID spellPublicId);

    // Items
    CharacterResponse addItem(UUID characterPublicId, AddItemRequest request);
    CharacterResponse removeItem(UUID characterPublicId, UUID itemPublicId);
    CharacterResponse toggleEquippedItem(UUID characterPublicId, UUID itemPublicId);


}
