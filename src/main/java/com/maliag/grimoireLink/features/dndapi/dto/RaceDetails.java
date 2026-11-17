package com.maliag.grimoireLink.features.dndapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RaceDetails {

    private String index;
    private String name;
    private List<DndReference> traits;

}
