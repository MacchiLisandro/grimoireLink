package com.maliag.grimoireLink.features.users.services;

import com.maliag.grimoireLink.features.users.dtos.UserRegisterRequest;
import com.maliag.grimoireLink.features.users.dtos.UserResponse;
import com.maliag.grimoireLink.features.users.dtos.UserUpdateNameRequest;
import com.maliag.grimoireLink.features.users.dtos.UserUpdatePasswordRequest;

import java.util.UUID;

public interface UserService {
    UserResponse register (UserRegisterRequest request);
    UserResponse updateName(UUID id, UserUpdateNameRequest request);
    void updatePassword(UUID id, UserUpdatePasswordRequest request);
    UserResponse getUser(UUID id);
    UserResponse getLoggedUser();


}
