package com.maliag.grimoireLink.features.characters.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class LevelUpRequest {

    @Min(2)
    @Max(20)
    private int newLevel;

    private String subclassIndex;
}
