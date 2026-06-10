package com.maliag.grimoireLink.features.spellsXCharacter.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SpellResponse {

    private UUID publicId;
    private String name;
    private boolean prepared;
}
