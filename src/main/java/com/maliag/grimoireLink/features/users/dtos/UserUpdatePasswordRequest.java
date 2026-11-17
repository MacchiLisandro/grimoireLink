package com.maliag.grimoireLink.features.users.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePasswordRequest {
    @NotBlank
    @Size(min = 6)
    private String oldPassword;
    @NotBlank
    @Size(min = 6)
    private String newPassword;
}
