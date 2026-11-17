package com.maliag.grimoireLink.features.users.Auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String errorMessage = switch (authException) {
            case BadCredentialsException e -> "Credenciales inválidas";
            case DisabledException e -> "Cuenta deshabilitada";
            case LockedException e -> "Cuenta bloqueada";
            case AccountExpiredException e -> "Cuenta expirada";
            case CredentialsExpiredException e -> "Credenciales expiradas";
            case InsufficientAuthenticationException e -> "Autenticación insuficiente";
            case AuthenticationServiceException e -> "Error en el servicio de autenticación";
            default -> "Error de autenticación: " + authException.getMessage();
        };
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("error", errorMessage);
        responseData.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        responseData.put("path", request.getRequestURI());
        response.getWriter().write(mapper.writeValueAsString(responseData));
        response.getWriter().flush();
    }
}