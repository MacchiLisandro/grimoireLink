package com.maliag.grimoireLink.features.characters;


import com.maliag.grimoireLink.features.background.BackgroundEntity;
import com.maliag.grimoireLink.features.background.BackgroundRepository;
import com.maliag.grimoireLink.features.backgroundSkills.BackGroundSkillsEntity;
import com.maliag.grimoireLink.features.backgroundSkills.BackgroundSkillsRepository;
import com.maliag.grimoireLink.features.characters.dto.CharacterCreateRequest;
import com.maliag.grimoireLink.features.characters.dto.CharacterResponse;
import com.maliag.grimoireLink.features.characters.dto.CharacterUpdateRequest;
import com.maliag.grimoireLink.features.dndapi.DnDApiService;
import com.maliag.grimoireLink.features.dndapi.dto.ClassDetail;
import com.maliag.grimoireLink.features.dndapi.dto.DndReference;
import com.maliag.grimoireLink.features.featuresXCharacter.FeatureXCharacterEntity;
import com.maliag.grimoireLink.features.featuresXCharacter.FeatureXCharacterRepository;
import com.maliag.grimoireLink.features.itemsXCharacter.ItemType;
import com.maliag.grimoireLink.features.itemsXCharacter.ItemsXCharacterEntity;
import com.maliag.grimoireLink.features.itemsXCharacter.ItemsXCharacterRepository;
import com.maliag.grimoireLink.features.itemsXCharacter.dto.AddItemRequest;
import com.maliag.grimoireLink.features.skillsXCharacter.SkillsXCharacterEntity;
import com.maliag.grimoireLink.features.skillsXCharacter.SkillsXCharacterRepository;
import com.maliag.grimoireLink.features.spellsXCharacter.SpellsXCharacterEntity;
import com.maliag.grimoireLink.features.spellsXCharacter.SpellsXCharacterRepository;
import com.maliag.grimoireLink.features.spellsXCharacter.dto.AddSpellRequest;
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

        List<SkillsXCharacterEntity> skills = buildBackgroundSkills(character, background);
        skillsXCharacterRepository.saveAll(skills);

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

    public CharacterEntity getCharacterByPublicId(UUID publicId){
        return characterRepository.findBypublicId(publicId)
                .orElseThrow(()-> new EntityNotFoundException("Personaje no encontrado"));
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
        if (newHp > character.getMaxHp()){
            newHp=character.getMaxHp();
        }

        character.setCurrentHp(newHp);

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
/// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public CharacterResponse addSpell(UUID characterPublicId, AddSpellRequest request) {

        String username=SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new EntityNotFoundException("Character not found"));

        validateAccess(character,username);

        boolean equipped=spellsXCharacterRepository.existsByCharacterAndSpellIndex(character, request.getSpellIndex());

        if (equipped){
            throw new EntityNotFoundException("Error el hechizo ya existe dentro");
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


    ///  terminar metodos
    @Override
    //@Transactional? dirty check o no?
    public CharacterResponse removeSpell(UUID characterPublicId, UUID spellPublicId) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new EntityNotFoundException("Character not found"));

        validateAccess(character,username);
        SpellsXCharacterEntity spells=spellsXCharacterRepository.findByPublicId(spellPublicId)
                .orElseThrow(()-> new EntityNotFoundException("Spell not exist"));

        if (!spells.getCharacter().getId().equals(character.getId())){
            throw new IllegalArgumentException("No le pertenece el hechizo");
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
            .orElseThrow(()-> new EntityNotFoundException("Character not found"));

    validateAccess(character,username);

    SpellsXCharacterEntity spell=spellsXCharacterRepository.findByPublicId(spellPublicId)
            .orElseThrow(()->new EntityNotFoundException("Spell not exist"));

        if (!spell.getCharacter().getId().equals(character.getId())) {
            throw new IllegalArgumentException("No le pertenece el hechizo");
        }
        spell.setPrepared(!spell.isPrepared());
        spellsXCharacterRepository.save(spell);

        return buildResponse(character);

    }

    @Override
    public CharacterResponse addItem(UUID characterPublicId, AddItemRequest request) {

        String username=SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        CharacterEntity character=characterRepository.findBypublicId(characterPublicId)
                .orElseThrow(()->new EntityNotFoundException("Character not found"));

        validateAccess(character,username);

        boolean alreadyHave=itemsXCharacterRepository
                .existsByCharacterAndItemIndexAndItemType(character,request.getItemIndex(),request.getItemType());

        if (alreadyHave)throw new IllegalArgumentException("character already have this item");

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
                .orElseThrow(()->new EntityNotFoundException("Character not found"));

        validateAccess(character,username);

        ItemsXCharacterEntity item=itemsXCharacterRepository.findByPublicId(itemPublicId)
                .orElseThrow(()->new EntityNotFoundException("item not found"));

        if (!item.getCharacter().getId().equals(character.getId())){
            throw new IllegalArgumentException("No le pertenece el item");
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
                .orElseThrow(()->new EntityNotFoundException("character not found"));

        validateAccess(character,username);

        ItemsXCharacterEntity item=itemsXCharacterRepository.findByPublicId(itemPublicId)
                .orElseThrow(()->new EntityNotFoundException("item not exist"));

        if (!item.getCharacter().getId().equals(character.getId())) {
            throw new IllegalArgumentException("No le pertenece el item");
        }
        item.setEquipped(!item.getEquipped());
        itemsXCharacterRepository.save(item);

        return buildResponse(character);


    }

/// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    /// ///////////////////////////////////////////////////////////////////////////////////////
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

    /// //////////////////////////////////////////////////////////
    private void validateAccess(CharacterEntity character, String username) {
        UUID publicCampaign = character.getUsersXCampaignEntity().getCampaign().getPublicId();

        usersXCampaignRepository
                .findByUser_Credentials_UsernameAndCampaign_PublicId(username, publicCampaign)
                .orElseThrow(() -> new EntityNotFoundException("No tenés acceso a este personaje"));
    }

        /// ////////////////para resolver que tipo de items es lo que trae////////////////////////////////////////////
        private String resolveItemName(String itemIndex, ItemType itemType) {
            if (itemType == ItemType.EQUIPMENT) {
                return dnDApiService.getEquipmentByIndex(itemIndex).getName();
            }
            return dnDApiService.getMagicItemByIndex(itemIndex).getName();
        }
}
