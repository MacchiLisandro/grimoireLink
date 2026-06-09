package com.maliag.grimoireLink.features.characters;

import com.maliag.grimoireLink.features.usersXCampaign.UsersXCampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {

    Optional<CharacterEntity> findByUsersXCampaignEntity_Id(Long uxcId);

    Optional<CharacterEntity> findBypublicId(UUID publicId);

    List<CharacterEntity> findByUsersXCampaignEntity_Campaign_PublicId(UUID campaingPublicId);

    boolean existsByUsersXCampaignEntityAndStatus(UsersXCampaignEntity usersXCampaignEntity, CharacterStatus status);

}
