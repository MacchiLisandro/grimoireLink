package com.maliag.grimoireLink.features.dndapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpellcastingSlots {

    @JsonProperty("cantrips_known")
    private int cantripsKnown;

    @JsonProperty("spells_known")
    private int spellsKnown;

    @JsonProperty("spell_slots_level_1")
    private int spellSlotsLevel1;

    @JsonProperty("spell_slots_level_2")
    private int spellSlotsLevel2;

    @JsonProperty("spell_slots_level_3")
    private int spellSlotsLevel3;

    @JsonProperty("spell_slots_level_4")
    private int spellSlotsLevel4;

    @JsonProperty("spell_slots_level_5")
    private int spellSlotsLevel5;

    @JsonProperty("spell_slots_level_6")
    private int spellSlotsLevel6;

    @JsonProperty("spell_slots_level_7")
    private int spellSlotsLevel7;

    @JsonProperty("spell_slots_level_8")
    private int spellSlotsLevel8;

    @JsonProperty("spell_slots_level_9")
    private int spellSlotsLevel9;



}
