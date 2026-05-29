package com.maliag.grimoireLink.features.users;

import com.maliag.grimoireLink.features.campaign.UsersXCampaignEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Column (name = "public_id", unique = true, nullable = false, updatable = false)
    private UUID publicId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UsersXCampaignEntity> usersXCampaign;

    @PrePersist
    protected void onCreate() {
        if (publicId==null){
            this.publicId = UUID.randomUUID();
        }
    }
}
