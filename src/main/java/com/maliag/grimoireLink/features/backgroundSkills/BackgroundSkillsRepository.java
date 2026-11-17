package com.maliag.grimoireLink.features.backgroundSkills;

import com.maliag.grimoireLink.features.background.model.BackgroundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BackgroundSkillsRepository extends JpaRepository<BackGroundSkillsEntity,Long> {

    List<BackGroundSkillsEntity>findByBackground(BackgroundEntity background);
}
