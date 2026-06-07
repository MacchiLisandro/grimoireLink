package com.maliag.grimoireLink.features.featuresXCharacter;

import com.maliag.grimoireLink.features.characters.CharacterEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "feature_x_character")
public class FeatureXCharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "public_id", unique = true, nullable = false, updatable = false)
    private UUID publicId;

    @ManyToOne
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterEntity character;

    @Column (name = "feature_index", nullable = false)
    private String featureIndex;

    @Column (name = "name", nullable = false)
    private String name;

    @Column(name = "source", nullable = false)
    private String source;

    @PrePersist
    void onCreate() {
        if (publicId==null){
            this.publicId = UUID.randomUUID();
        }
    }
}
