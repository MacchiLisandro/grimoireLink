package com.maliag.grimoireLink.features.background.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BackgroundRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Size(max = 65535)
    private String description;
}
