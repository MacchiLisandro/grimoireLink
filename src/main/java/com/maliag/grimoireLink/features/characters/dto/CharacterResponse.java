package com.maliag.grimoireLink.features.characters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.maliag.grimoireLink.features.characters.CharacterStatus;
import com.maliag.grimoireLink.features.dndapi.dto.SpellcastingSlots;
import com.maliag.grimoireLink.features.skillsXCharacter.dto.CharacterSkillResponse;
import com.maliag.grimoireLink.features.spellsXCharacter.dto.SpellResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterResponse {

    private UUID publicId;
    private String name;
    private String race;
    private String characterClass;
    private String subclass;
    private String background;
    private Integer level;
    private Integer maxHP;
    private Integer currentHP;
    private Integer gold;
    private CharacterStatus status;

    /// Calcularlo desde el mapper
    private int proficiencyBonus;

    private  Integer strengh;
    private  Integer dexterity;
    private  Integer wisdom;
    private  Integer charisma;
    private  Integer constitution;
    private  Integer intelligence;

    /// calcular desde mapper tmb
    private Integer strenghMod;
    private Integer dexterityMod;
    private Integer wisdomMod;
    private Integer charismaMod;
    private Integer constitutionMod;
    private Integer intelligenceMod;

    /// referencias a la dndapi
    private List<SpellResponse>knowSpells;
    private List<String>featureIndices;
    private List<CharacterItemResponse>items;
    private List<CharacterSkillResponse> skills;

    @JsonInclude(Include.NON_NULL)/// Por si es un no caster
    private SpellcastingSlots spellSlots;

    private String summary;



}
