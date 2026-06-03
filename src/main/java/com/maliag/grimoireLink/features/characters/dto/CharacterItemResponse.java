package com.maliag.grimoireLink.features.characters.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterItemResponse {

    private String itemIndex;
    private String name;
    private Boolean equipped;
    private Integer quantity;

}
