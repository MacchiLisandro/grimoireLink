package com.maliag.grimoireLink.features.characters;

import com.maliag.grimoireLink.features.characters.dto.CharacterItemResponse;
import com.maliag.grimoireLink.features.characters.dto.CharacterResponse;
import com.maliag.grimoireLink.features.featuresXCharacter.FeatureXCharacterEntity;
import com.maliag.grimoireLink.features.itemsXCharacter.ItemsXCharacterEntity;
import com.maliag.grimoireLink.features.spellsXCharacter.SpellsXCharacterEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CharacterMapper {



    public CharacterResponse toResponse(CharacterEntity character,
                                        List<SpellsXCharacterEntity> spells,
                                        List<FeatureXCharacterEntity> features,
                                        List<ItemsXCharacterEntity> items){

        int proeficiencyBonus=proficiencyBonus(character.getLevel());


        return CharacterResponse.builder()
                .publicId(character.getPublicId())
                .name(character.getName())
                .race(character.getRaceName())
                .characterClass(character.getClassName())
                ///.subclass(character.getsubclass)
                ///.backgroundIndex(c.getBackgroundIndex())
                .level(character.getLevel())
                .maxHP(character.getMaxHP())
                .currentHP(character.getCurrentHp())
                .gold(character.getGold())
                .status(character.getStatus())
                .proficiencyBonus(proeficiencyBonus)
                .strengh(character.getStrength())
                .dexterity(character.getDexterity())
                .intelligence(character.getIntelligence())
                .wisdom(character.getWisdom())
                .charisma(character.getCharisma())
                /// aca usamos el helper juas
                .strenghMod(modifier(character.getStrength()))
                .dexterityMod(modifier(character.getDexterity()))
                .wisdomMod(modifier(character.getWisdom()))
                .charismaMod(modifier(character.getCharisma()))

                .knowSpells(spells.stream()
                        .map(SpellsXCharacterEntity::getSpellIndex)
                        .toList())
                .featureIndices(features.stream()
                        .map(FeatureXCharacterEntity::getFeatureIndex)
                        .toList())
                .items(items.stream()
                        .map(this::toItemResponse)
                        .toList())

                /// Fijarnos si hacemos un helper para resumir el personaje de manera bonita
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



}
