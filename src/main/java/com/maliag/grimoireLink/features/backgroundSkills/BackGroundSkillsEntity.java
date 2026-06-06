package com.maliag.grimoireLink.features.backgroundSkills;

import com.maliag.grimoireLink.features.background.BackgroundEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "background_skills")
public class BackGroundSkillsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "skill_index",nullable = false)
    private String skillIndex;

    @Column(name = "skill_name",nullable = false)
    private String skillname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "background_id",nullable = false)
    private BackgroundEntity background;

}
