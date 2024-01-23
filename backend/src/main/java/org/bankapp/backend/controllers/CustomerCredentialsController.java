package org.bankapp.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.ChangePasswordDTO;
import org.bankapp.backend.services.security.CustomerCredentialsService;
import org.bankapp.backend.services.security.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class CustomerCredentialsController {

    private final CustomerCredentialsService customerCredentialsService;

    @PostMapping("private/credentials/change-password")
    public void changePassword(@CookieValue(name = SessionService.SESSION_COOKIE_NAME) String sessionId,
                               @Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        customerCredentialsService.changePassword(sessionId,
                changePasswordDTO.getPasswordParts(),
                changePasswordDTO.getNewPassword());
    }


}
