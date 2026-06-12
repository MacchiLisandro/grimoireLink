package com.maliag.grimoireLink.features.characters.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CharacterUpdateRequest {

    @NotBlank(message = "the name must be change")
    private String name;



}
