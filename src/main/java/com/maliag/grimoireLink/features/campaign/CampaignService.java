package com.maliag.grimoireLink.features.campaign;

import com.maliag.grimoireLink.features.campaign.dto.CampaignRequest;
import com.maliag.grimoireLink.features.campaign.dto.CampaignResponse;
import com.maliag.grimoireLink.features.campaign.dto.UpdateCampaignRequest;
import com.maliag.grimoireLink.features.usersXCampaign.dto.CampaignMemberResponse;

import java.util.List;
import java.util.UUID;

public interface CampaignService {
    CampaignResponse getByPublicId(UUID publicId);
    List<CampaignResponse> getAllCampaignsByUserId(UUID userId);
    List<CampaignMemberResponse> getAllCampaignMembers(UUID publicId);
    CampaignResponse createCampaign(CampaignRequest request);
    CampaignResponse updateCampaign(UUID publicId, UpdateCampaignRequest request);
    void deleteCampaign(UUID publicId);
    CampaignResponse joinCampaign(String inviteCode);
}
