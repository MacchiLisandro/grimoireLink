package com.maliag.grimoireLink.features.usersXCampaign.dto;

import com.maliag.grimoireLink.features.usersXCampaign.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CampaignMemberResponse {
    private UUID publicId;
    private String name;
    private String email;
    private LocalDateTime joinDate;
    private Role role;
}
