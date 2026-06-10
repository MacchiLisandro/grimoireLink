package com.maliag.grimoireLink.features.background.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class BackGroundSeedDto {

    private String index;
    private String name;
    private String description;
    private String language;
    private List<String>skills;


}
