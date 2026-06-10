package com.maliag.grimoireLink.features.users.services;

import com.maliag.grimoireLink.features.users.Credentials.CredentialsEntity;
import com.maliag.grimoireLink.features.users.Credentials.CredentialsRepository;
import com.maliag.grimoireLink.features.users.models.UserEntity;
import com.maliag.grimoireLink.features.users.mappers.UserMapper;
import com.maliag.grimoireLink.features.users.repositories.UserRepository;
import com.maliag.grimoireLink.features.users.dtos.*;
import com.maliag.grimoireLink.features.users.exceptions.UserAlreadyExistsException;
import com.maliag.grimoireLink.features.users.exceptions.UserNotFoundException;
import com.maliag.grimoireLink.features.users.exceptions.UserNotRegisteredOrPasswordIncorrectException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CredentialsRepository credentialsRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        UserEntity user = userRepository.save(userMapper.registerToEntity(request));
        CredentialsEntity credentials = CredentialsEntity.builder()
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .user(user)
                .build();
        credentialsRepository.save(credentials);
        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponse updateName(UUID id, UserUpdateNameRequest request) {
        UserEntity user = userRepository.findByPublicId(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setName(request.getName());
        return userMapper.toResponse(user);
    }

    @Transactional
    public void updatePassword(UUID id, UserUpdatePasswordRequest request) {
        CredentialsEntity credentials = userRepository.findByPublicId(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"))
                .getCredentials();
        if (!passwordEncoder.matches(request.getOldPassword(), credentials.getPassword())) {
            throw new UserNotRegisteredOrPasswordIncorrectException("Password incorrect");
        }
        credentials.setPassword(passwordEncoder.encode(request.getNewPassword()));
        System.out.println("password cambiandose updaitiandose");
        credentialsRepository.save(credentials);
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(UUID id) {
        return userMapper.toResponse(userRepository.findByPublicId(id)
                .orElseThrow(() -> new UserNotFoundException("User not found")));
    }

    @Transactional(readOnly = true)
    public UserResponse getLoggedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toResponse(user);
    }
}