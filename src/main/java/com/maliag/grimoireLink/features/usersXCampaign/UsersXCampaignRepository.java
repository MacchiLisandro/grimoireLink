package com.maliag.grimoireLink.features.usersXCampaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersXCampaignRepository extends JpaRepository<UsersXCampaignEntity, Long> {






    /// CAMBIAR CUANDO TENGAMOS JWT
    Optional<UsersXCampaignEntity> findFirstByCampaign_PublicId(UUID campaignPublicId);
}
