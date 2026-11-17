package com.maliag.grimoireLink.features.campaign.repository;

import com.maliag.grimoireLink.features.campaign.model.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CampaignRepository extends JpaRepository<CampaignEntity, Long> {
    Optional<CampaignEntity> findByPublicId(UUID publicId);
    Boolean existsByInviteCode(String inviteCode);

    Optional<CampaignEntity> findByInviteCode(String inviteCode);
}
