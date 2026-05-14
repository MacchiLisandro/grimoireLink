package com.maliag.grimoireLink.features.campaign;

import com.maliag.grimoireLink.features.users.Role;
import com.maliag.grimoireLink.features.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="users_x_campaign")
@EqualsAndHashCode(onlyExplicitlyIncluded = false)
public class UsersXCampaignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "public_id", nullable = false)
    private UUID publicId;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private CampaignEntity campaign;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    protected void onCreate(){
        if(publicId==null){
            this.publicId = UUID.randomUUID();
        }
        if(joinDate==null){
            joinDate = LocalDateTime.now();
        }
    }

}
