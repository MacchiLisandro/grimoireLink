package com.maliag.grimoireLink.features.characters.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterEncounterResponse {
    private UUID publicId;
    private String name;
    private String raceName;
    private String className;
    private String subclassName;
    private Integer level;
    private Integer maxHp;
    private Integer currentHp;
    private Integer strength;
    private Integer dexterity;
    private Integer constitution;
    private Integer intelligence;
    private Integer wisdom;
    private Integer charisma;
    private Integer gold;
    private String status;
}
