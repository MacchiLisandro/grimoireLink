package com.maliag.grimoireLink.features.characters;


import com.maliag.grimoireLink.features.characters.dto.CharacterCreateRequest;
import com.maliag.grimoireLink.features.characters.dto.CharacterResponse;
import com.maliag.grimoireLink.features.characters.dto.CharacterUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface CharacterService {

    CharacterResponse createCharacter(CharacterCreateRequest request,
                                      UUID campaingPublicID);

    CharacterResponse getCharacterById(UUID CharacterPublicId);

    List<CharacterResponse> getCharacterByCampaing(UUID campaignPublicId);

    CharacterResponse updateCharacter(UUID characterPublicId,
                                      CharacterUpdateRequest request);

    void deleteCharacter(UUID characterPublicId);

    CharacterResponse updateHp(UUID characterPublicId,
                                 int newHp);


    CharacterResponse updateGold(UUID characterPublicId,
                                 int newGold);


}
