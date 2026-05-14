package com.maliag.grimoireLink.features.campaign;

import com.maliag.grimoireLink.features.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="users_x_campaign")
public class UsersXCampaignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private CampaignEntity campaign;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate;

    @PrePersist
    protected void onCreate(){
        if(joinDate==null){
            joinDate = LocalDateTime.now();
        }
    }

}
