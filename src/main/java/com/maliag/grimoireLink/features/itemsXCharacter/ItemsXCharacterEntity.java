package com.maliag.grimoireLink.features.itemsXCharacter;

import com.maliag.grimoireLink.features.characters.CharacterEntity;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.HashCodeExclude;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "items_x_character")
public class ItemsXCharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Column (name = "public_id", unique = true, nullable = false, updatable = false)
    private UUID publicId;

    @ManyToOne
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterEntity character;

    @Column(name = "item_index", nullable = false)
    private String itemIndex;

    @Column (name = "name", nullable = false)
    private String name;

    @Column(name = "equipped", nullable = false)
    private Boolean equipped;

    @Column(name = "quantity",nullable = false)
    private Integer quantity;

    @PrePersist
    protected void onCreate() {
        if (publicId == null) {
            this.publicId = UUID.randomUUID();
        }
        if (equipped == null) {
            equipped = false;
        }
    }
}
