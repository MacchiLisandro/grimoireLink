package com.maliag.grimoireLink.features.usersXCampaign;

import com.maliag.grimoireLink.features.campaign.CampaignMapper;
import com.maliag.grimoireLink.features.users.UserEntity;
import com.maliag.grimoireLink.features.usersXCampaign.dto.CampaignMemberResponse;
import org.springframework.stereotype.Component;

@Component
public class UsersXCampaignMapper {
    public CampaignMemberResponse toResponse(UsersXCampaignEntity uxc){
        return CampaignMemberResponse.builder()
                .publicId(uxc.getUser().getPublicId())
                .name(uxc.getUser().getName())
                .email(uxc.getUser().getEmail())
                .joinDate(uxc.getJoinDate())
                .role(uxc.getRole())
                .build();
    }
}
