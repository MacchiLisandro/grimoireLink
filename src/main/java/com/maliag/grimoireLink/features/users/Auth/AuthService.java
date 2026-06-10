package com.maliag.grimoireLink.features.users.Auth;

import com.maliag.grimoireLink.common.exceptions.UnauthorizedException;
import com.maliag.grimoireLink.features.users.Credentials.CredentialsEntity;
import com.maliag.grimoireLink.features.users.Credentials.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class    AuthService {

    private final CredentialsRepository credentialsRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse authenticate(AuthRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        CredentialsEntity credentials = credentialsRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        String accessToken = jwtService.generateToken(credentials);
        String refreshToken = jwtService.generateRefreshToken(credentials);
        credentials.setRefreshToken(refreshToken);
        return new AuthResponse(accessToken, refreshToken);
    }

    @Transactional
    public AuthResponse refreshAccessToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        CredentialsEntity credentials = credentialsRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("User not found"));
        if (!credentials.getRefreshToken().equals(refreshToken)) {
            throw new UnauthorizedException("Refresh token does not match");
        }
        if (!jwtService.validateRefreshToken(refreshToken, credentials)) {
            throw new UnauthorizedException("Refresh token expired or invalid");
        }
        String newAccessToken = jwtService.generateToken(credentials);
        String newRefreshToken = jwtService.generateRefreshToken(credentials);
        credentials.setRefreshToken(newRefreshToken);
        return new AuthResponse(newAccessToken, newRefreshToken);
    }
}
