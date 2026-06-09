package com.maliag.grimoireLink.features.users;

import com.maliag.grimoireLink.features.users.dtos.UserRegisterRequest;
import com.maliag.grimoireLink.features.users.dtos.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "usersXCampaign", ignore = true)
    @Mapping(target = "credentials", ignore = true)
    UserEntity registerToEntity (UserRegisterRequest request);
    UserResponse toResponse (UserEntity entity);
}
