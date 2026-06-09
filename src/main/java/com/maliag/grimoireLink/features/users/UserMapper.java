package com.maliag.grimoireLink.features.users;

import com.maliag.grimoireLink.features.users.dtos.UserRegisterRequest;
import com.maliag.grimoireLink.features.users.dtos.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity registerToEntity (UserRegisterRequest request);
    UserResponse toResponse (UserEntity entity);
}
