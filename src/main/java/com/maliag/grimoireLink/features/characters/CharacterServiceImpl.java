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
import org.springframework.security.core.context.SecurityContextHolder;
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
       String username = SecurityContextHolder
               .getContext()
                .getAuthentication()
                .getName();

        UsersXCampaignEntity member=usersXCampaignRepository
                .findByUser_Credentials_UsernameAndCampaign_PublicId(username,publicCampaingId)
                .orElseThrow(()-> new EntityNotFoundException("No se encontro  jugador con la campaña"));

        boolean isAlive=characterRepository.existsByUsersXCampaignEntityAndStatus(member,CharacterStatus.ALIVE);

        if (isAlive){
            throw  new EntityNotFoundException("No puede tener mas de un personaje vivo a la vez");
        }

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

        int conModifier = Math.floorDiv(request.getConstitution() - 10, 2);
        int avgXLvl = (hitDice / 2 ) + 1;
        int maxHp = (hitDice + conModifier) + (request.getLevel() - 1 )
                * (avgXLvl + conModifier);

        CharacterEntity character=characterMapper.toEntity(
                request,member,background,raceName,className,subclassName,maxHp);

        characterRepository.save(character);

        List<FeatureXCharacterEntity>features =new ArrayList<>();
        features.addAll(buildFeatures(character,
                dnDApiService.getFeaturesForClass(request.getClassIndex(), request.getLevel()), "CLASS"));
        features.addAll(buildFeatures(character,
                dnDApiService.getRaceFeatures(request.getRaceIndex()), "RACE"));

        featureXCharacterRepository.saveAll(features);

        return characterMapper.toResponse(character,List.of(),features,List.of());


    }

    @Override
    @Transactional
    public CharacterResponse getCharacterById(UUID CharacterPublicId) {

        String username=SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character=characterRepository.
                findBypublicId(CharacterPublicId)
                .orElseThrow(()->new EntityNotFoundException("Personaje no encontrado"));


        validateAccess(character, username);

        return buildResponse(character);
    }

    @Override
    @Transactional
    public List<CharacterResponse> getCharacterByCampaing(UUID campaignPublicId) {

        String username=SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        usersXCampaignRepository.findByUser_Credentials_UsernameAndCampaign_PublicId(username,campaignPublicId)
                .orElseThrow(()->new RuntimeException("Error, no hay acceso a la campaña"));

        List<CharacterEntity>characters=characterRepository
                .findByUsersXCampaignEntity_Campaign_PublicId(campaignPublicId);

        List<CharacterResponse>response=new ArrayList<>();
        for (CharacterEntity character : characters){
            response.add(buildResponse(character));
        }
        return response;
    }

    @Override
    @Transactional
    public CharacterResponse updateCharacter(UUID characterPublicId, CharacterUpdateRequest request) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new EntityNotFoundException("No existe el personaje"));
        /// fijarse si solo dejamos el nombre para cambiar jajaj

        validateAccess(character,username);

        character.setName(request.getName());
        characterRepository.save(character);

        return buildResponse(character); 
    }

    @Transactional
    @Override
    public void deleteCharacter(UUID characterPublicId) {

        String username=SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character = characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new EntityNotFoundException("Error, notfound"));

        validateAccess(character,username);

        character.setStatus(CharacterStatus.DEAD);
        characterRepository.save(character);
    }

    @Transactional
    @Override
    public CharacterResponse updateHp(UUID characterPublicId, int newHp) {

        String username=SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new EntityNotFoundException("No existe el jugador"));

        validateAccess(character,username);

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

    @Transactional
    @Override
    public CharacterResponse updateGold(UUID characterPublicId, int newGold) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new EntityNotFoundException("No existe el jugador"));

        validateAccess(character,username);

        if (newGold < 0) throw new IllegalArgumentException("El oro no puede ser negativo");

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

    /// /////////////////////////////////////////////////////////
    private List<FeatureXCharacterEntity> buildFeatures(
            CharacterEntity character, List<DndReference> references, String source) {
        List<FeatureXCharacterEntity> features = new ArrayList<>();
        for (DndReference f : references) {
            features.add(FeatureXCharacterEntity.builder()
                    .character(character)
                    .featureIndex(f.getIndex())
                    .name(f.getName())
                    .source(source)
                    .build());
        }
        return features;
    }
    /// //////////////////////////////////////////////////////////
    private void validateAccess(CharacterEntity character, String username) {
        UUID publicCampaign = character.getUsersXCampaignEntity().getCampaign().getPublicId();

        usersXCampaignRepository
                .findByUser_Credentials_UsernameAndCampaign_PublicId(username, publicCampaign)
                .orElseThrow(() -> new EntityNotFoundException("No tenés acceso a este personaje"));
    }


}
