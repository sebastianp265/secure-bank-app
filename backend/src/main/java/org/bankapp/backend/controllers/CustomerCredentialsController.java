package org.bankapp.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.ChangePasswordDTO;
import org.bankapp.backend.interceptors.AuthorizationInterceptor;
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
    public void changePassword(@RequestAttribute(name = AuthorizationInterceptor.CUSTOMER_ID_ATTRIBUTE) String customerId,
                               @Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        customerCredentialsService.changePassword(customerId,
                changePasswordDTO.getPassword(),
                changePasswordDTO.getNewPassword());
    }


}
