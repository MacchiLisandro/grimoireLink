package com.maliag.grimoireLink.features.characters;

import com.maliag.grimoireLink.features.background.BackgroundEntity;
import com.maliag.grimoireLink.features.characters.dto.CharacterCreateRequest;
import com.maliag.grimoireLink.features.characters.dto.CharacterItemResponse;
import com.maliag.grimoireLink.features.characters.dto.CharacterResponse;
import com.maliag.grimoireLink.features.featuresXCharacter.FeatureXCharacterEntity;
import com.maliag.grimoireLink.features.itemsXCharacter.ItemsXCharacterEntity;
import com.maliag.grimoireLink.features.spellsXCharacter.SpellsXCharacterEntity;
import com.maliag.grimoireLink.features.spellsXCharacter.dto.SpellResponse;
import com.maliag.grimoireLink.features.usersXCampaign.UsersXCampaignEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CharacterMapper {



    public CharacterResponse toResponse(CharacterEntity character,
                                        List<SpellsXCharacterEntity> spells,
                                        List<FeatureXCharacterEntity> features,
                                        List<ItemsXCharacterEntity> items){

        int proficiencyBonus=proficiencyBonus(character.getLevel());


        return CharacterResponse.builder()
                .publicId(character.getPublicId())
                .name(character.getName())
                .race(character.getRaceName())
                .characterClass(character.getClassName())
                .subclass(character.getSubclassName())
                .background(resolveBackground(character))
                .level(character.getLevel())
                .maxHP(character.getMaxHp())
                .currentHP(character.getCurrentHp())
                .gold(character.getGold())
                .status(character.getStatus())
                .proficiencyBonus(proficiencyBonus)
                /// Las tiradas de abilityScore van por acua
                .strengh(character.getStrength())
                .dexterity(character.getDexterity())
                .intelligence(character.getIntelligence())
                .constitution(character.getConstitution())
                .wisdom(character.getWisdom())
                .charisma(character.getCharisma())
                /// aca usamos el helper juas
                .strenghMod(modifier(character.getStrength()))
                .dexterityMod(modifier(character.getDexterity()))
                .wisdomMod(modifier(character.getWisdom()))
                .charismaMod(modifier(character.getCharisma()))
                .intelligenceMod(modifier(character.getIntelligence()))
                .constitutionMod(modifier(character.getConstitution()))

                /// Items, Hechizos,Features del personaje
                .knowSpells(mapSpells(spells))
                .featureIndices(mapFeatures(features))
                .items(mapItems(items))
                /// Su hoja de personaje
                .summary(buildHojaPersonaje(character,proficiencyBonus))
                .build();
    }


    public CharacterEntity toEntity(CharacterCreateRequest request,
                                    UsersXCampaignEntity members,
                                    BackgroundEntity background,
                                    String racename,
                                    String className,
                                    String subclassName,
                                    int maxHP){
        return CharacterEntity.builder()
                .usersXCampaignEntity(members)
                .name(request.getName())
                .background(background)
                .raceIndex(request.getRaceIndex())
                .raceName(racename)
                .classIndex(request.getClassIndex())
                .className(className)
                .subclassIndex(request.getSubclassIndex())
                .subclassName(subclassName)
                .level(request.getLevel())
                .maxHp(maxHP)
                .currentHp(maxHP)
                .strength(request.getStrength())
                .dexterity(request.getDexterity())
                .constitution(request.getConstitution())
                .intelligence(request.getIntelligence())
                .wisdom(request.getWisdom())
                .charisma(request.getCharisma())
                .gold(request.getGold() != null ? request.getGold() : 0)
                .build();
    }





    /// Helper para el mod de estadisticas
    private int modifier(int score){
        return (int)Math.floor((score - 10)/2.0);
    }

    /// Helper para sacar el bonus de proeficiencia
    /// cada 4 lvls suben el bono

    private int proficiencyBonus(int level){
        return (int) (Math.ceil(level/4.0) + 1 );
    }

    private CharacterItemResponse toItemResponse(ItemsXCharacterEntity item){
        return CharacterItemResponse.builder()
                .name(item.getName())
                .itemIndex(item.getItemIndex())
                .equipped(item.getEquipped())
                .quantity(item.getQuantity())
                .build();
    }

    /// eliminar despues de cerrar el tema del seeder de backgrounds
    private String resolveBackground(CharacterEntity c) {
        if (c.getBackground() == null) {
            return null;
        }
        return c.getBackground().getName();
    }

    /// Helpers para el mapeo

    private List<String>mapFeatures(List<FeatureXCharacterEntity> features){
        List<String> resultado=new ArrayList<>();

        if (features == null){
            return resultado;
        }
        for (FeatureXCharacterEntity feature:features){
            resultado.add(feature.getFeatureIndex());
        }
        return resultado;
    }

    private List<SpellResponse>mapSpells(List<SpellsXCharacterEntity>spells){
        List<SpellResponse>resultado=new ArrayList<>();
        if (spells == null)return resultado;

        for (SpellsXCharacterEntity spell: spells){
            SpellResponse dto =SpellResponse.builder()
                    .publicId(spell.getPublicId())
                    .name(spell.getName())
                    .prepared(spell.isPrepared())
                    .build();

            resultado.add(dto);
        }

        return  resultado;
    }

    private List<CharacterItemResponse>mapItems(List<ItemsXCharacterEntity> items){
        List<CharacterItemResponse> resultado=new ArrayList<>();
        if (items== null)return resultado;

        for (ItemsXCharacterEntity item : items){
            CharacterItemResponse dto= CharacterItemResponse.builder()
                    .name(item.getName())
                    .quantity(item.getQuantity())
                    .equipped(item.getEquipped())
                    .build();
            resultado.add(dto);
        }
        return resultado;
    }

    private String buildHojaPersonaje(CharacterEntity character, int proefBonus){
        return String.format(
                "%s — %s %s nivel %d (PB +%d)",
                character.getName(),
                character.getRaceName(),
                character.getClassName(),
                character.getLevel(),
                proefBonus
        );
    }

}
