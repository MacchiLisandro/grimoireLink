package com.maliag.grimoireLink.features.skillsXCharacter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterSkillResponse {
    private UUID publicId;
    private String skillIndex;
    private String name;
    private Integer proficiency;
}