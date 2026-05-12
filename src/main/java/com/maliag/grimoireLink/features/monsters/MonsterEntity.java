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
public class MonsterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "public_id", unique = true, nullable = false, updatable = false)
    private UUID publicId;
    @Column (name = "name", nullable = false)
    private String name;
    @Column (name = "level", nullable = false)
    private Integer level;
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
    @Column (name = "is_alive", nullable = false)
    private Boolean isAlive;

    @PrePersist
    protected void onCreate() {
        if (publicId==null){
            this.publicId = UUID.randomUUID();
        }
    }
}
