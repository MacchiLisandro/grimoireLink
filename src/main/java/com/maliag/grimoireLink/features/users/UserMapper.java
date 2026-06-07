package com.maliag.grimoireLink.features.users;

import com.maliag.grimoireLink.features.users.Credentials.dtos.UserLoginRequest;
import com.maliag.grimoireLink.features.users.Credentials.dtos.UserRegisterRequest;
import com.maliag.grimoireLink.features.users.Credentials.dtos.UserResponse;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserEntity LoginToEntity (UserLoginRequest request);
    UserEntity RegisterToEntity (UserRegisterRequest request);
    UserResponse toResponse (UserEntity entity);
}
