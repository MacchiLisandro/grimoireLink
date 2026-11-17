package com.maliag.grimoireLink.features.itemsXCharacter.dto;

import com.maliag.grimoireLink.features.itemsXCharacter.ItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemRequest {
    @NotBlank
    private String itemIndex;
    @NotNull
    private ItemType itemType;
}
