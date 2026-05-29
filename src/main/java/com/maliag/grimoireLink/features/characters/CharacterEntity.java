package com.maliag.grimoireLink.features.characters;

import com.maliag.grimoireLink.features.campaign.UsersXCampaignEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="characters")

public class CharacterEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="character_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uxc_id",nullable = false,unique = true)
    private UsersXCampaignEntity usersXCampaignEntity;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(name = "background_index")
    private String backgroundIndex;

    @Column(name = "background")
    private String background;

    @Column(name = "race_index")
    private String raceIndex;

    @Column(name = "race")
    private String raceName;

    @Column(name = "class_index")
    private String classIndex;

    @Column(name = "class")
    private String className;

    @Min(1) @Max(20)
    @Column(name = "level")
    private  Integer level;

    @Column(name = "max_hp")
    private Integer maxHP;

    @Column(name = "current_hp")
    private Integer currentHp;

    private Integer strength;
    private Integer dexterity;
    private Integer constitution;
    private Integer intelligence;
    private Integer wisdom;
    private Integer charisma;

    @Builder.Default
    private Integer gold = 0;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CharacterStatus status = CharacterStatus.ALIVE;

}
