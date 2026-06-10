package com.maliag.grimoireLink.features.characters.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CharacterItemResponse {

    private UUID publicId;
    private String itemIndex;
    private String name;
    private Boolean equipped;
    private Integer quantity;

}
