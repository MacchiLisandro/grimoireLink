package com.maliag.grimoireLink.features.dndapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DndReference {

    private String index;
    private String name;
    private String url;


}
