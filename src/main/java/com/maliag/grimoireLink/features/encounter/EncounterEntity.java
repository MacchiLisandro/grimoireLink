package com.maliag.grimoireLink.features.encounter;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "encounters")
public class EncounterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @Column(name = "encounter_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EncounterType encounterType;

    @Column(name = "challenge_rating", nullable = false)
    @Enumerated(EnumType.STRING)
    private ChallengeRating challengeRating;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "encounter_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private String encounterStatus;

    @PrePersist
    void onCreate(){
        if(publicId==null){
            this.publicId = UUID.randomUUID();
        }
    }
}
