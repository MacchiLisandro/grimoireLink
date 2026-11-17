package com.maliag.grimoireLink.features.spellsXCharacter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddSpellRequest {
    @NotBlank
    private String spellIndex;
}
