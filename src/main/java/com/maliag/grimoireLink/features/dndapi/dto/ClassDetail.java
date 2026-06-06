package com.maliag.grimoireLink.features.dndapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassDetail {

    @JsonProperty("index")
    private String index;

    @JsonProperty("name")
    private  String name;

    @JsonProperty("hit_dice")
    private int hitdice;




}
