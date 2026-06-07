package com.maliag.grimoireLink.features.usersXCampaign;

import com.maliag.grimoireLink.features.campaign.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Repository
public interface UsersXCampaignRepository extends JpaRepository<UsersXCampaignEntity, Long> {

    /// CAMBIAR CUANDO TENGAMOS JWT
    Optional<UsersXCampaignEntity> findFirstByCampaign_PublicId(UUID campaignPublicId);

    ///cuando implementemos security sacamos el id del usuario de ahi
    @Query("SELECT uxc.campaign FROM UsersXCampaignEntity AS uxc WHERE uxc.user.publicId = :userPublicId")
    List<CampaignEntity> findCampaignsByUserPublicId(@Param("userPublicId") UUID userPublicId);
}
