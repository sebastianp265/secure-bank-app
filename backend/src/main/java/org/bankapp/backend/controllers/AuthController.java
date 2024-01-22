package org.bankapp.backend.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.ChangePasswordDTO;
import org.bankapp.backend.dtos.LoginRequestDTO;
import org.bankapp.backend.services.security.AuthService;
import org.bankapp.backend.services.security.SessionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/change-password")
    public void changePassword(@CookieValue(name = SessionService.SESSION_COOKIE_NAME) String sessionId,
                               @Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        authService.changePassword(sessionId,
                changePasswordDTO.getPasswordParts(),
                changePasswordDTO.getNewPassword());
    }

    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginRequestDTO loginRequestDTO,
                      HttpServletResponse response) {
        authService.login(
                loginRequestDTO.getCustomerId(),
                loginRequestDTO.getPasswordParts(),
                response);
    }


}
