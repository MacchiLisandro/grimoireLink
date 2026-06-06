package com.maliag.grimoireLink.features.characters.dto;

import com.maliag.grimoireLink.features.spellsXCharacter.SpellsXCharacterEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Max.List;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterCreateRequest {

    @NotNull(message = "Error, se necesita una membresia de camapaña")
    private Long pxcId;

    @NotBlank(message = "Debe de elegir un nombre")
    private String name;

    @NotBlank(message = "Se debe elegir una raza")
    private String raceIndex;

    @NotBlank(message = "Se debe elegir una clase")
    private String classIndex;

    /// puede venir null si no es el nivel adecuado o no llega a el nivel
    private String subclassIndex;

    @NotBlank(message = "Se debe tener un background")
    private String backgroundIndex;

    @Min(value = 1, message = "El nivel debe ser minimo 1")
    @Max(value = 20, message = "el nivel maximo es 20")
    private Integer level;

    @Min(1)
    @Max(20)
    private Integer strength;
    @Min(1)
    @Max(20)
    private Integer dexterity;
    @Min(1)
    @Max(20)
    private Integer constitution;
    @Min(1)
    @Max(20)
    private Integer intelligence;
    @Min(1)
    @Max(20)
    private Integer wisdom;
    @Min(1)
    @Max(20)
    private Integer charisma;

    @Min(value =0, message = "El oro no puede ser negativo")
    private Integer gold;

}