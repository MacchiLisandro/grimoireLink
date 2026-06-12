package com.maliag.grimoireLink.features.background.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "backgrounds")
public class BackgroundEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @Column(name = "background_index")
    private String backgroundIndex;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name ="Languages")
    private String languages;

    @PrePersist
    void onCreate(){
        if(publicId==null){
            this.publicId = UUID.randomUUID();
        }
    }
}
