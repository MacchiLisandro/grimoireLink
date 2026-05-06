package com.maliag.grimoireLink.features.dndapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DndReferenceList {

    private Integer count; /// busca por literal "count"
    private List<DndReference> results; /// busca por literal "results"


}
