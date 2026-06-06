package com.maliag.grimoireLink.features.dndapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassLevelDetail {

    @JsonProperty("level")
    private Integer level;

    /// Feats que gana la claseXNivel
    @JsonProperty("features")
    private List<DndReference>features;
/// Los Slots depende el nivel(Si es no caster, devuelve null)
    @JsonProperty("spellcasting")
    private SpellcastingSlots spellcasting;




}
