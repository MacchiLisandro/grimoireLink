package com.maliag.grimoireLink.features.usersXCampaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersXCampaignRepository extends JpaRepository<UsersXCampaignEntity, Long> {
}
