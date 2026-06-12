package com.maliag.grimoireLink.features.characters.model;

import com.maliag.grimoireLink.features.characters.enums.CharacterStatus;
import com.maliag.grimoireLink.features.usersXCampaign.UsersXCampaignEntity;
import com.maliag.grimoireLink.features.background.model.BackgroundEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

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

    @Column(name = "public_id",nullable = false,unique = true,updatable = false)
    private UUID publicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uxc_id", nullable = false)
    private UsersXCampaignEntity usersXCampaignEntity;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "background_id")
    private BackgroundEntity background;

    @Column(name = "race_index")
    private String raceIndex;

    @Column(name = "race")
    private String raceName;

    @Column(name = "class_index")
    private String classIndex;

    @Column(name = "class")
    private String className;

    @Column(name = "subclass_index")
    private String subclassIndex;

    @Column(name = "subclass_name")
    private String subclassName;

    @Min(1) @Max(20)
    @Column(name = "level")
    private  Integer level;

    @Column(name = "max_hp")
    private Integer maxHp;

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

    @PrePersist
    void onCreate(){
        if (publicId == null){
            publicId=UUID.randomUUID();
        }
    }

}
