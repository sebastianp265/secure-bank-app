package org.bankapp.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.ChangePasswordDTO;
import org.bankapp.backend.services.AuthenticationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/change-password/{resetToken}")
    public void changePassword(@PathVariable String resetToken,
                              @RequestBody ChangePasswordDTO changePasswordDTO) {
        authenticationService.changePassword(resetToken,
                changePasswordDTO.getPasswordParts(),
                changePasswordDTO.getNewPassword());
    }
}
