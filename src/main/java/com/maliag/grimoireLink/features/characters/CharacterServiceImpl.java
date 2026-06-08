package com.maliag.grimoireLink.features.characters;


import com.maliag.grimoireLink.features.background.BackgroundEntity;
import com.maliag.grimoireLink.features.background.BackgroundRepository;
import com.maliag.grimoireLink.features.characters.dto.CharacterCreateRequest;
import com.maliag.grimoireLink.features.characters.dto.CharacterResponse;
import com.maliag.grimoireLink.features.characters.dto.CharacterUpdateRequest;
import com.maliag.grimoireLink.features.dndapi.DnDApiService;
import com.maliag.grimoireLink.features.dndapi.dto.ClassDetail;
import com.maliag.grimoireLink.features.dndapi.dto.DndReference;
import com.maliag.grimoireLink.features.featuresXCharacter.FeatureXCharacterEntity;
import com.maliag.grimoireLink.features.featuresXCharacter.FeatureXCharacterRepository;
import com.maliag.grimoireLink.features.itemsXCharacter.ItemsXCharacterEntity;
import com.maliag.grimoireLink.features.itemsXCharacter.ItemsXCharacterRepository;
import com.maliag.grimoireLink.features.spellsXCharacter.SpellsXCharacterEntity;
import com.maliag.grimoireLink.features.spellsXCharacter.SpellsXCharacterRepository;
import com.maliag.grimoireLink.features.usersXCampaign.UsersXCampaignEntity;
import com.maliag.grimoireLink.features.usersXCampaign.UsersXCampaignRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final UsersXCampaignRepository usersXCampaignRepository;
    private final BackgroundRepository backgroundRepository;
    private final FeatureXCharacterRepository featureXCharacterRepository;
    private final SpellsXCharacterRepository spellsXCharacterRepository;
    private final ItemsXCharacterRepository itemsXCharacterRepository;
    private final CharacterMapper characterMapper;
    private final DnDApiService dnDApiService;


    @Override
    @Transactional
    public CharacterResponse createCharacter(CharacterCreateRequest request,
                                             UUID publicCampaingId){
        ///String username=SecurityContextHolder.blablabla

        UsersXCampaignEntity member=usersXCampaignRepository
                .findFirstByCampaign_PublicId(publicCampaingId)
                .orElseThrow(()-> new EntityNotFoundException("No se encontro  jugador con la campaña"));

        BackgroundEntity background=backgroundRepository
                .findBybackgroundIndex(request.getBackgroundIndex())
                .orElseThrow(()->new EntityNotFoundException("No existe tal back"));


        ClassDetail classDetail=dnDApiService.getclassdetails(request.getClassIndex());
        String className=classDetail.getName();
        int hitDice=classDetail.getHitdice();

        String raceName=dnDApiService.getRaceByIndex(request.getRaceIndex()).getName();

        String subclassName=null;

        if (request.getSubclassIndex() != null){
            subclassName=dnDApiService.getSubclass(
                    request.getSubclassIndex()).getName();
        }

        int conModifier = (int) ((request.getConstitution() -10)/2.0);
        int maxHp=hitDice + conModifier;

        CharacterEntity character=characterMapper.toEntity(
                request,member,background,raceName,className,subclassName,maxHp);

        characterRepository.save(character);

        List<FeatureXCharacterEntity>features =new ArrayList<>();
        List<DndReference>classfeature=dnDApiService.getFeaturesForClass(request.getClassIndex(),request.getLevel());
        for (DndReference f: classfeature){
            features.add(FeatureXCharacterEntity.builder()
                    .character(character)
                    .featureIndex(f.getIndex())
                    .name(f.getName())
                    .source("CLASS")
                    .build());
        }

        List<DndReference>raceFeastures=
                dnDApiService.getRaceFeatures(request.getRaceIndex());
        for (DndReference f:raceFeastures){
            features.add(FeatureXCharacterEntity.builder()
                    .character(character)
                    .featureIndex(f.getIndex())
                    .name(f.getName())
                    .source("RACE")
                    .build());
        }

        featureXCharacterRepository.saveAll(features);

        return characterMapper.toResponse(character,List.of(),features,List.of());


    }

    @Override
    @Transactional
    public CharacterResponse getCharacterById(UUID CharacterPublicId) {

        CharacterEntity character=characterRepository.
                findBypublicId(CharacterPublicId)
                .orElseThrow(()->new EntityNotFoundException("Personaje no encontrado"));

        List<SpellsXCharacterEntity> spells= spellsXCharacterRepository
                .findByCharacter(character);

        List<FeatureXCharacterEntity>features=featureXCharacterRepository
                .findByCharacter(character);

        List<ItemsXCharacterEntity>items=itemsXCharacterRepository
                .findByCharacter(character);

        return characterMapper.toResponse(character,spells,features,items);

    }

    @Override
    public List<CharacterResponse> getCharacterByCampaing(UUID campaignPublicId) {

        List<CharacterEntity>characters=characterRepository
                .findByUsersXCampaignEntity_Campaign_PublicId(campaignPublicId);

        List<CharacterResponse>response=new ArrayList<>();
        for (CharacterEntity character : characters){
            List<SpellsXCharacterEntity>spells=spellsXCharacterRepository.findByCharacter(character);
            List<FeatureXCharacterEntity>features=featureXCharacterRepository.findByCharacter(character);
            List<ItemsXCharacterEntity>items=itemsXCharacterRepository.findByCharacter(character);

            response.add(characterMapper.toResponse(character,spells,features,items));
        }
        return response;
    }

    @Override
    @Transactional
    public CharacterResponse updateCharacter(UUID characterPublicId, CharacterUpdateRequest request) {

        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new EntityNotFoundException("No existe el personaje"));
        /// fijarse si solo dejamos el nombre para cambiar jajaj
        character.setName(request.getName());

        characterRepository.save(character);

        return buildResponse(character); 
    }

    @Override
    public void deleteCharacter(UUID characterPublicId) {

    }

    @Override
    public CharacterResponse updateHp(UUID characterPublicId, int newHp) {
        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new EntityNotFoundException("No existe el jugador"));
        if (newHp <0){
            newHp=0;
        }
        if (newHp > character.getMaxHP()){
            newHp=character.getMaxHP();
        }

        character.setCurrentHp(newHp);
        characterRepository.save(character);

        return buildResponse(character);
    }

    @Override
    public CharacterResponse updateGold(UUID characterPublicId, int newGold) {

        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new EntityNotFoundException("No existe el jugador"));

        if (newGold < 0) throw new IllegalArgumentException("El oro no puede ser negattivo");

        character.setGold(newGold);
        characterRepository.save(character);

        return buildResponse(character); 
    }



    /// Helper para no repetir codigo al dope///////////////////
    private CharacterResponse buildResponse(CharacterEntity character) {
        List<SpellsXCharacterEntity> spells =
                spellsXCharacterRepository.findByCharacter(character);
        List<FeatureXCharacterEntity> features =
                featureXCharacterRepository.findByCharacter(character);
        List<ItemsXCharacterEntity> items =
                itemsXCharacterRepository.findByCharacter(character);

        return characterMapper.toResponse(character, spells, features, items);
    }


}
