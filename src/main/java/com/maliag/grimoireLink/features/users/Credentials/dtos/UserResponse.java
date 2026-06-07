package com.maliag.grimoireLink.features.users.Credentials.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private UUID publicID;

    private String name;

    private String email;
}
