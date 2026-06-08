package com.maliag.grimoireLink.features.users;

import com.maliag.grimoireLink.features.users.Credentials.CredentialsEntity;
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
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse register (UserRegisterRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        return userMapper.toResponse(userRepository.save(userMapper.RegisterToEntity(request)));
    }

    @Transactional
    public UserResponse updateName(UUID id, UserUpdateNameRequest request){
        UserEntity user = userRepository.findByPublicId(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setName(request.getName());
        return userMapper.toResponse(user);
    }

    @Transactional
    public void updatePassword(UUID id, UserUpdatePasswordRequest request){
        CredentialsEntity credentials = userRepository.findByPublicId(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"))
                .getCredentials();
        if(!passwordEncoder.matches(request.getOldPassword(), credentials.getPassword())){
            throw new UserNotRegisteredOrPasswordIncorrectException("Password incorrect");
        }
        credentials.setPassword(passwordEncoder.encode(request.getNewPassword()));
    }

    @Transactional (readOnly = true)
    public UserResponse getUser(UUID id){
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
