package com.maliag.grimoireLink.features.campaign;

import com.maliag.grimoireLink.features.usersXCampaign.UsersXCampaignEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

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

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "invite_code", nullable = false, unique = true)
    private String inviteCode;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CampaignStatus status;

    @OneToMany(mappedBy = "campaign")
    private List<UsersXCampaignEntity> users = new ArrayList<>();

    @PrePersist
    void onCreate(){
        if(publicId==null){
            this.publicId = UUID.randomUUID();
        }
        if(inviteCode==null){
            inviteCode = String.format("%06d",
                    ThreadLocalRandom.current().nextInt(0, 999999));
        }
        if(status==null){
            status = CampaignStatus.ACTIVE;
        }
    }
}
