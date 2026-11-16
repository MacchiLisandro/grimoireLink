package com.maliag.grimoireLink.features.monsters.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonsterReferenceResponse {
    private String index;
    private String name;
    private String url;
}