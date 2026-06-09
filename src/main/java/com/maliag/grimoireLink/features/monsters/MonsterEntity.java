package com.maliag.grimoireLink.features.monsters;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "monsters")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MonsterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Column (name = "public_id", unique = true, nullable = false, updatable = false)
    private UUID publicId;

    @Column (name = "name", nullable = false)
    private String name;

    @Column (name = "challengeRating", nullable = false)
    private Double challengeRating;

    @Column (name = "max_hp", nullable = false)
    private Integer maxHp;

    @Column (name = "current_hp", nullable = false)
    private Integer currentHp;

    @Column (name = "strength", nullable = false)
    private Integer strength;

    @Column (name = "dexterity", nullable = false)
    private Integer dexterity;

    @Column (name = "constitution", nullable = false)
    private Integer constitution;

    @Column (name = "intelligence", nullable = false)
    private Integer intelligence;

    @Column (name = "wisdom", nullable = false)
    private Integer wisdom;

    @Column (name = "charisma", nullable = false)
    private Integer charisma;

    @Column (name = "is_alive", nullable = false)
    private Boolean isAlive;

    @PrePersist
    void onCreate() {
        if (publicId==null){
            this.publicId = UUID.randomUUID();
        }
    }
}
