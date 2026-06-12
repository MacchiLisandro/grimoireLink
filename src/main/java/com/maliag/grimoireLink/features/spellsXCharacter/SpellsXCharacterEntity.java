package com.maliag.grimoireLink.features.spellsXCharacter;

import com.maliag.grimoireLink.features.characters.model.CharacterEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "spells_x_character")
public class SpellsXCharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "public_id", unique = true, nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UUID publicId;

    @ManyToOne
    @JoinColumn(name = "character_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CharacterEntity character;

    @Column(name = "spell_index", nullable = false)
    private String spellIndex;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "prepared", nullable = false)
    private boolean prepared;

    @PrePersist
    void onCreate() {
        if (publicId == null) {
            this.publicId = UUID.randomUUID();
        }
       this.prepared=false;
    }
}