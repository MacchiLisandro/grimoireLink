package com.maliag.grimoireLink.features.users.controller;
import com.maliag.grimoireLink.features.users.services.UserService;
import com.maliag.grimoireLink.features.users.dtos.UserResponse;
import com.maliag.grimoireLink.features.users.dtos.UserUpdateNameRequest;
import com.maliag.grimoireLink.features.users.dtos.UserUpdatePasswordRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@RequestParam UUID id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping
    public ResponseEntity<UserResponse> getLoggedUser(){
        return ResponseEntity.ok(userService.getLoggedUser());
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<UserResponse> updateName(@PathVariable UUID id,
                                                   @Valid @RequestBody UserUpdateNameRequest request){
        return ResponseEntity.ok(userService.updateName(id, request));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<UserResponse> updatePassword(@PathVariable UUID id,
                                                   @Valid @RequestBody UserUpdatePasswordRequest request){
        return ResponseEntity.noContent().build();
    }
}
