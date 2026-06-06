package com.maliag.grimoireLink.features.users;

import com.maliag.grimoireLink.features.users.dtos.UserLoginRequest;
import com.maliag.grimoireLink.features.users.dtos.UserRegisterRequest;
import com.maliag.grimoireLink.features.users.dtos.UserResponse;
import com.maliag.grimoireLink.features.users.exceptions.UserAlreadyExistsException;
import com.maliag.grimoireLink.features.users.exceptions.UserNotRegisteredOrPasswordIncorrectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse register (UserRegisterRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        return userMapper.toResponse(userRepository.save(userMapper.RegisterToEntity(request)));
    }

    @Transactional
    public UserResponse login (UserLoginRequest request){
        if (!userRepository.existsByEmail(request.getEmail())) {
            throw new UserNotRegisteredOrPasswordIncorrectException("email doesn't exists or password is incorrect");
        }
        if (userRepository.findByEmail(request.getEmail()))

    }
}
