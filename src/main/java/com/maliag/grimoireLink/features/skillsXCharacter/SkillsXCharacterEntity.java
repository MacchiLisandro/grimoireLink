package com.maliag.grimoireLink.features.skillsXCharacter;

import com.maliag.grimoireLink.features.characters.CharacterEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "skills_x_character")
public class SkillsXCharacterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterEntity character;

    @Column(name = "skill_index", nullable = false)
    private String skillIndex;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "proficiency", nullable = false)
    private Integer proficiency;
    @PrePersist
    void onCreate(){
        if(publicId==null){
            this.publicId = UUID.randomUUID();
        }
    }
}
