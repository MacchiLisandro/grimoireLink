package com.maliag.grimoireLink.features.campaign;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="campaigns")
public class CampaignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID publicId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "inviteCode", nullable = false)
    private Long inviteCode;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status;

    @PrePersist
    protected void onCreate(){
        this.publicId = UUID.randomUUID();
    }
}
