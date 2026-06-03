package com.maliag.grimoireLink.features.characters.dto;

import com.maliag.grimoireLink.features.characters.CharacterStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.boot.internal.Abstract;

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
    private List<String>knowSpells;
    private List<String>featureIndices;
    private List<CharacterItemResponse>items;

    // un resumen del personaje(a la hora de hacer el mapper vamos a implementar un helper de descripcion de personaje)
    private String summary;



}
