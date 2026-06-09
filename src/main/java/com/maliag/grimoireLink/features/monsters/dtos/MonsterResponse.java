package com.maliag.grimoireLink.features.monsters.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonsterResponse {
    private UUID publicId;
    private String name;
    private Integer level;
    private Integer maxHp;
    private Integer currentHp;
    private Integer strength;
    private Integer dexterity;
    private Integer constitution;
    private Boolean isAlive;
}
