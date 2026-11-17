package com.maliag.grimoireLink.features.background.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BackgroundResponse {

    private UUID publicId;
    private String name;
    private String description;
}
