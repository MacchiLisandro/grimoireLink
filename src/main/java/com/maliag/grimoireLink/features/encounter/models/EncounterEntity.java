package com.maliag.grimoireLink.features.encounter.models;

import com.maliag.grimoireLink.features.characters.CharacterEntity;
import com.maliag.grimoireLink.features.encounter.enums.EncounterDifficulty;
import com.maliag.grimoireLink.features.encounter.enums.EncounterStatus;
import com.maliag.grimoireLink.features.encounter.enums.EncounterType;
import com.maliag.grimoireLink.features.monsters.models.MonsterEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "encounters")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EncounterEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @Column(name = "encounter_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EncounterType encounterType;

    @Column(name = "encounter_difficulty", nullable = false)
    @Enumerated(EnumType.STRING)
    private EncounterDifficulty encounterDifficulty;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "encounter_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EncounterStatus encounterStatus;

    @ManyToMany
    @JoinTable(
            name = "encounters_x_character",
            joinColumns = @JoinColumn(name = "encounter_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id")
    )
    private List<CharacterEntity> characters;

    @ManyToMany
    @JoinTable(
            name = "encounters_x_monster",
            joinColumns = @JoinColumn(name = "encounter_id"),
            inverseJoinColumns = @JoinColumn(name = "monster_id")
    )
    private List<MonsterEntity> monsters;

    @PrePersist
    void onCreate(){
        if(publicId==null) {
            this.publicId = UUID.randomUUID();
        }
    }
}
