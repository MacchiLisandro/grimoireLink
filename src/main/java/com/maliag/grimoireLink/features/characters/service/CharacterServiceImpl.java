package com.maliag.grimoireLink.features.characters.service;

import com.maliag.grimoireLink.features.background.model.BackgroundEntity;
import com.maliag.grimoireLink.features.background.repository.BackgroundRepository;
import com.maliag.grimoireLink.features.background.exceptions.BackgroundNotFoundException;
import com.maliag.grimoireLink.features.backgroundSkills.BackGroundSkillsEntity;
import com.maliag.grimoireLink.features.backgroundSkills.BackgroundSkillsRepository;
import com.maliag.grimoireLink.features.campaign.exceptions.CharacterAlreadyAliveException;
import com.maliag.grimoireLink.features.campaign.exceptions.MembershipNotFoundException;
import com.maliag.grimoireLink.features.characters.enums.CharacterStatus;
import com.maliag.grimoireLink.features.characters.dto.CharacterCreateRequest;
import com.maliag.grimoireLink.features.characters.dto.CharacterResponse;
import com.maliag.grimoireLink.features.characters.dto.CharacterUpdateRequest;
import com.maliag.grimoireLink.features.characters.dto.LevelUpRequest;
import com.maliag.grimoireLink.features.characters.exceptions.*;
import com.maliag.grimoireLink.features.characters.mapper.CharacterMapper;
import com.maliag.grimoireLink.features.characters.model.CharacterEntity;
import com.maliag.grimoireLink.features.characters.repository.CharacterRepository;
import com.maliag.grimoireLink.features.dndapi.DnDApiService;
import com.maliag.grimoireLink.features.dndapi.dto.ClassDetail;
import com.maliag.grimoireLink.features.dndapi.dto.DndReference;
import com.maliag.grimoireLink.features.dndapi.dto.SpellcastingSlots;
import com.maliag.grimoireLink.features.featuresXCharacter.FeatureXCharacterEntity;
import com.maliag.grimoireLink.features.featuresXCharacter.FeatureXCharacterRepository;
import com.maliag.grimoireLink.features.itemsXCharacter.ItemType;
import com.maliag.grimoireLink.features.itemsXCharacter.ItemsXCharacterEntity;
import com.maliag.grimoireLink.features.itemsXCharacter.ItemsXCharacterRepository;
import com.maliag.grimoireLink.features.itemsXCharacter.dto.AddItemRequest;
import com.maliag.grimoireLink.features.itemsXCharacter.exceptions.ItemAlreadyAddedException;
import com.maliag.grimoireLink.features.itemsXCharacter.exceptions.ItemNotFoundException;
import com.maliag.grimoireLink.features.skillsXCharacter.SkillsXCharacterEntity;
import com.maliag.grimoireLink.features.skillsXCharacter.SkillsXCharacterRepository;
import com.maliag.grimoireLink.features.spellsXCharacter.SpellsXCharacterEntity;
import com.maliag.grimoireLink.features.spellsXCharacter.SpellsXCharacterRepository;
import com.maliag.grimoireLink.features.spellsXCharacter.dto.AddSpellRequest;
import com.maliag.grimoireLink.features.spellsXCharacter.exceptions.SpellAlreadyAddedException;
import com.maliag.grimoireLink.features.spellsXCharacter.exceptions.SpellNotFoundException;
import com.maliag.grimoireLink.features.usersXCampaign.UsersXCampaignEntity;
import com.maliag.grimoireLink.features.usersXCampaign.UsersXCampaignRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    /// ///////////////////////////////////////////////////////////////////////
    private final CharacterRepository characterRepository;
    private final UsersXCampaignRepository usersXCampaignRepository;
    /// ///////////////////////////////////////////////////////////////////////
    private final BackgroundRepository backgroundRepository;
    private final FeatureXCharacterRepository featureXCharacterRepository;
    private final SpellsXCharacterRepository spellsXCharacterRepository;
    private final ItemsXCharacterRepository itemsXCharacterRepository;
    private final SkillsXCharacterRepository skillsXCharacterRepository;
    private final BackgroundSkillsRepository backgroundSkillsRepository;
    /// //////////////////////////////////////////////////////////////////////
    private final CharacterMapper characterMapper;
    private final DnDApiService dnDApiService;


    ////////////////////////////////Create Character////////////////////////////////////////////////////////////////////

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
                .orElseThrow(()-> new CharacterNotFoundException("Player not found in campaign"));

        boolean isAlive=characterRepository.existsByUsersXCampaignEntityAndStatus(member, CharacterStatus.ALIVE);

        if (isAlive){
            throw  new CharacterAlreadyAliveException("cant have two characters alive in the same campaign");
        }

        BackgroundEntity background=backgroundRepository
                .findBybackgroundIndex(request.getBackgroundIndex())
                .orElseThrow(()->new BackgroundNotFoundException("Background not found"));

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

        List<SkillsXCharacterEntity> skills = buildBackgroundSkills(character, background);
        skillsXCharacterRepository.saveAll(skills);

        SpellcastingSlots spellSlots =
                dnDApiService.getSpellSlots(request.getClassIndex(), request.getLevel());


        return characterMapper.toResponse(character,List.of(),features,List.of(),skills,spellSlots);
    }
///   ////////////////////////////////////Gets Response//////////////////////////////////////////////////////////////////////////
    @Override
    @Transactional(readOnly = true)
    public CharacterResponse getCharacterByPublicId(UUID characterPublicId, UUID campaignPublicId) {

        String username=SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        usersXCampaignRepository.findByUser_Credentials_UsernameAndCampaign_PublicId(username,campaignPublicId)
                .orElseThrow(()->new MembershipNotFoundException("Error, no hay acceso a la campaña"));

        CharacterEntity character=findCharacterByPublicId(characterPublicId);

        return buildResponse(character);
    }

    public CharacterEntity findCharacterByPublicId(UUID publicId){
        return characterRepository.findBypublicId(publicId)
                .orElseThrow(()-> new CharacterNotFoundException("Personaje no encontrado"));
    }
/// /////////////////////////////Get Character by Campaing///////////////////////////////////////////////////////////
    @Override
    @Transactional(readOnly = true)
    public List<CharacterResponse> getCharacterByCampaing(UUID campaignPublicId) {

        String username=SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        usersXCampaignRepository.findByUser_Credentials_UsernameAndCampaign_PublicId(username,campaignPublicId)
                .orElseThrow(()->new MembershipNotFoundException("Error, no hay acceso a la campaña"));

        List<CharacterEntity>characters=characterRepository
                .findByUsersXCampaignEntity_Campaign_PublicId(campaignPublicId);

        List<CharacterResponse>response=new ArrayList<>();
        for (CharacterEntity character : characters){
            response.add(buildResponse(character));
        }
        return response;
    }
/// ///////////////////////////////Update Name//////////////////////////////////////////////////////////////
    @Override
    @Transactional
    public CharacterResponse updateCharacter(UUID characterPublicId, CharacterUpdateRequest request) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new CharacterNotFoundException("Character not found"));

        validateAccess(character,username);

        character.setName(request.getName());
        characterRepository.save(character);

        return buildResponse(character); 
    }
////////////////////////////////////Delete(cambia el enum)//////////////////////////////////////////////////////////////////
    @Transactional
    @Override
    public void deleteCharacter(UUID characterPublicId) {

        String username=SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character = characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new CharacterNotFoundException("Error, character not found"));

        validateAccess(character,username);

        character.setStatus(CharacterStatus.DEAD);
        characterRepository.save(character);
    }
/// //////////////////////////////Update hp/////////////////////////////////////////////////////////////////////
    @Transactional
    @Override
    public CharacterResponse updateHp(UUID characterPublicId, int newHp) {

        String username=SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new CharacterNotFoundException("Character not found"));

        validateAccess(character,username);

        if (newHp <=0){
            newHp=0;
            character.setStatus(CharacterStatus.UNCONSCIOUS);
        }

        if (newHp > character.getMaxHp()){
            newHp=character.getMaxHp();
        }

        character.setCurrentHp(newHp);

        return buildResponse(character);
    }
/// /////////////////////////////////////Update gold///////////////////////////////////////////////////////////////////////////
    @Transactional
    @Override
    public CharacterResponse updateGold(UUID characterPublicId, int newGold) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new CharacterNotFoundException("Character not found"));

        validateAccess(character,username);

        if (newGold < 0) throw new InvalidGoldAmountException("Gold cannot be negative");

        character.setGold(newGold);
        characterRepository.save(character);

        return buildResponse(character); 
    }
/// ////////////////////////////////////////Level Up////////////////////////////////////////////////////////////////////////////
    @Override
    public CharacterResponse levelUp(UUID characterPublicId, LevelUpRequest request) {
        String username=SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()-> new CharacterNotFoundException("Character not found"));

        validateAccess(character,username);

        int oldLevel=character.getLevel();
        int newLevel=request.getNewLevel();

        if (newLevel <= oldLevel){
            throw new InvalidLevelException("the new level must be higher than current one");
        }

        ClassDetail classDetail=dnDApiService.getclassdetails(character.getClassIndex());
        int hitDice=classDetail.getHitdice();
        int conMod=Math.floorDiv(character.getConstitution() -10,2);
        int avgXLvl=(hitDice / 2) + 1;
        int hpIncrese=(newLevel - oldLevel) * (avgXLvl + conMod);

        character.setMaxHp(character.getMaxHp() + hpIncrese);
        character.setCurrentHp(character.getCurrentHp() + hpIncrese);

        if (character.getStatus() == CharacterStatus.UNCONSCIOUS && character.getCurrentHp() > 0){
            character.setStatus(CharacterStatus.ALIVE);
        }
        character.setLevel(newLevel);

        if (character.getSubclassIndex() == null && request.getSubclassIndex() != null){
            String subclassName=dnDApiService.getSubclass(request.getSubclassIndex()).getName();
            character.setSubclassIndex(request.getSubclassIndex());
            character.setSubclassName(subclassName);
        }

        characterRepository.save(character);

        List<DndReference>allFeaturesUpToNewLevel=dnDApiService.getFeaturesForClass(character.getClassIndex(),newLevel);

        Set<String> existingIndexes=featureXCharacterRepository.findByCharacter(character)
                .stream()
                .map(FeatureXCharacterEntity::getFeatureIndex)
                .collect(Collectors.toSet());

        List<FeatureXCharacterEntity>newFeatures=new ArrayList<>();
        for (DndReference r: allFeaturesUpToNewLevel){
            if (!existingIndexes.contains(r.getIndex())){
                newFeatures.add(FeatureXCharacterEntity.builder()
                        .character(character)
                        .featureIndex(r.getIndex())
                        .name(r.getName())
                        .source("CLASS")
                        .build());
            }
        }
        featureXCharacterRepository.saveAll(newFeatures);

        return buildResponse(character);
    }


    //////////////////////////////////Spells CRUD //////////////////////////////////////////////////////////////////////
    @Override
    public CharacterResponse addSpell(UUID characterPublicId, AddSpellRequest request) {

        String username=SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new CharacterNotFoundException("Character not found"));

        validateAccess(character,username);

        SpellcastingSlots slots = dnDApiService.getSpellSlots(
                character.getClassIndex(), character.getLevel());

        if (slots == null) {
            throw new ResourceOwnershipException(
                    "class" + character.getClassName() + "cant use spells");
        }

        boolean equipped=spellsXCharacterRepository.existsByCharacterAndSpellIndex(character, request.getSpellIndex());

        if (equipped){
            throw new SpellAlreadyAddedException("The spell is already added");
        }
        String spellname= dnDApiService.getSpellByIndex(request.getSpellIndex()).getName();

        SpellsXCharacterEntity spell =SpellsXCharacterEntity.builder()
                .character(character)
                .spellIndex(request.getSpellIndex())
                .name(spellname)
                .build();

        spellsXCharacterRepository.save(spell);

        return buildResponse(character);
    }



    @Override
    //@Transactional? dirty check o no?
    public CharacterResponse removeSpell(UUID characterPublicId, UUID spellPublicId) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new CharacterNotFoundException("Character not found"));

        validateAccess(character,username);
        SpellsXCharacterEntity spells=spellsXCharacterRepository.findByPublicId(spellPublicId)
                .orElseThrow(()-> new SpellNotFoundException("Spell not exist"));

        if (!spells.getCharacter().getId().equals(character.getId())){
            throw new ResourceOwnershipException("The spell does not belong to this character");
        }
        spellsXCharacterRepository.delete(spells);

        return buildResponse(character);
    }

    @Override
    public CharacterResponse togglePreparedSpell(UUID characterPublicId, UUID spellPublicId) {

    String username=SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

    CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
            .orElseThrow(()-> new CharacterNotFoundException("Character not found"));

    validateAccess(character,username);

    SpellsXCharacterEntity spell=spellsXCharacterRepository.findByPublicId(spellPublicId)
            .orElseThrow(()->new SpellNotFoundException("Spell not exist"));

        if (!spell.getCharacter().getId().equals(character.getId())) {
            throw new ResourceOwnershipException("The spell does not belong to this character");
        }
        spell.setPrepared(!spell.isPrepared());
        spellsXCharacterRepository.save(spell);

        return buildResponse(character);

    }
/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/// /////////////////////////////CRUD ITEM /////////////////////////////////////////////////////////////////////////////
    @Override
    public CharacterResponse addItem(UUID characterPublicId, AddItemRequest request) {

        String username=SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new CharacterNotFoundException("Character not found"));

        validateAccess(character,username);

        boolean alreadyHave=itemsXCharacterRepository
                .existsByCharacterAndItemIndexAndItemType(character,request.getItemIndex(),request.getItemType());

        if (alreadyHave)throw new ItemAlreadyAddedException("character already have this item");

        String itemname=resolveItemName(request.getItemIndex(),request.getItemType());

        ItemsXCharacterEntity item= ItemsXCharacterEntity.builder()
                .character(character)
                .itemIndex(request.getItemIndex())
                .name(itemname)
                .itemType(request.getItemType())
                .build();

        itemsXCharacterRepository.save(item);

        return buildResponse(character);

    }

    @Override
    public CharacterResponse removeItem(UUID characterPublicId, UUID itemPublicId) {

        String username=SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new CharacterNotFoundException("Character not found"));

        validateAccess(character,username);

        ItemsXCharacterEntity item=itemsXCharacterRepository.findByPublicId(itemPublicId)
                .orElseThrow(()->new ItemNotFoundException("item not found"));

        if (!item.getCharacter().getId().equals(character.getId())){
            throw new ResourceOwnershipException("No le pertenece el item");
        }
        itemsXCharacterRepository.delete(item);

        return buildResponse(character);


    }

    @Override
    public CharacterResponse toggleEquippedItem(UUID characterPublicId, UUID itemPublicId) {

        String username=SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new CharacterNotFoundException("character not found"));

        validateAccess(character,username);

        ItemsXCharacterEntity item=itemsXCharacterRepository.findByPublicId(itemPublicId)
                .orElseThrow(()->new EntityNotFoundException("item not exist"));

        if (!item.getCharacter().getId().equals(character.getId())) {
            throw new IllegalArgumentException("The item does not belong to this character");
        }
        item.setEquipped(!item.getEquipped());
        itemsXCharacterRepository.save(item);

        return buildResponse(character);


    }
/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    /// Helper para no repetir codigo///////////////////

    private CharacterResponse buildResponse(CharacterEntity character) {
        List<SpellsXCharacterEntity> spells =
                spellsXCharacterRepository.findByCharacter(character);
        List<FeatureXCharacterEntity> features =
                featureXCharacterRepository.findByCharacter(character);
        List<ItemsXCharacterEntity> items =
                itemsXCharacterRepository.findByCharacter(character);
        List<SkillsXCharacterEntity> skills =
                skillsXCharacterRepository.findByCharacter(character);
        SpellcastingSlots spellSlots =
                dnDApiService.getSpellSlots(character.getClassIndex(), character.getLevel());


        return characterMapper.toResponse(character, spells, features, items,skills,spellSlots);
    }

    /// //////////////////////////////Build Features////////////////////////////////////////////////////////////////////////////
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
    /// ///////////////////////////////Build Backs////////////////////////////////////////////////////////////////////////////
    private List<SkillsXCharacterEntity> buildBackgroundSkills(
            CharacterEntity character, BackgroundEntity background) {
        List<SkillsXCharacterEntity> skills = new ArrayList<>();

        List<BackGroundSkillsEntity> backgroundSkills =
                backgroundSkillsRepository.findByBackground(background);

        for (BackGroundSkillsEntity bgSkill : backgroundSkills) {
            skills.add(SkillsXCharacterEntity.builder()
                    .character(character)
                    .skillIndex(bgSkill.getSkillIndex())
                    .name(bgSkill.getSkillname())
                    .proficiency(1)
                    .build());
        }
        return skills;
    }

    /// ///////////////////////////////////Security//////////////////////////////////////////////////////////
    private void validateAccess(CharacterEntity character, String username) {
        UUID publicCampaign = character.getUsersXCampaignEntity().getCampaign().getPublicId();

        usersXCampaignRepository
                .findByUser_Credentials_UsernameAndCampaign_PublicId(username, publicCampaign)
                .orElseThrow(() -> new CharacterAccessDeniedException("You dont have access to this character"));
    }

        /// /////////////////////////////////Si es Magic o Equipment///////////////////////////////////////////////
        private String resolveItemName(String itemIndex, ItemType itemType) {
            if (itemType == ItemType.EQUIPMENT) {
                return dnDApiService.getEquipmentByIndex(itemIndex).getName();
            }
            return dnDApiService.getMagicItemByIndex(itemIndex).getName();
        }
}
