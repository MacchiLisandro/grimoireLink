package com.maliag.grimoireLink.features.users;

import com.maliag.grimoireLink.features.users.Credentials.dtos.UserLoginRequest;
import com.maliag.grimoireLink.features.users.Credentials.dtos.UserRegisterRequest;
import com.maliag.grimoireLink.features.users.Credentials.dtos.UserResponse;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-06T23:17:08-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.11 (Microsoft)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity LoginToEntity(UserLoginRequest request) {
        if ( request == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.email( request.getEmail() );
        userEntity.password( request.getPassword() );

        return userEntity.build();
    }

    @Override
    public UserEntity RegisterToEntity(UserRegisterRequest request) {
        if ( request == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.name( request.getName() );
        userEntity.email( request.getEmail() );
        userEntity.password( request.getPassword() );

        return userEntity.build();
    }

    @Override
    public UserResponse toResponse(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setName( entity.getName() );
        userResponse.setEmail( entity.getEmail() );

        return userResponse;
    }
}
