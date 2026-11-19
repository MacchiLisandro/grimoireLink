package com.maliag.grimoireLink.features.catalog.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BackgroundCatalogResponse {
    private String index;
    private String name;
    private String description;
}
