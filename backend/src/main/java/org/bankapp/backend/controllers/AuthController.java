package org.bankapp.backend.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.ChangePasswordDTO;
import org.bankapp.backend.dtos.LoginRequestDTO;
import org.bankapp.backend.dtos.RequestLoginResponseDTO;
import org.bankapp.backend.services.security.AuthService;
import org.bankapp.backend.services.security.SessionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginRequestDTO loginRequestDTO,
                      HttpServletResponse response) {
        authService.login(
                loginRequestDTO.getCustomerId(),
                loginRequestDTO.getPassword(),
                response);
    }

    @PostMapping("/request-login/{customerId}")
    public RequestLoginResponseDTO requestLogin(@PathVariable String customerId) {
        return authService.requestLogin(customerId);
    }
}
