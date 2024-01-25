package org.bankapp.backend.controllers.domain;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.ChangePasswordDTO;
import org.bankapp.backend.services.security.CustomerCredentialsService;
import org.bankapp.backend.services.security.SessionService;
import org.springframework.web.bind.annotation.*;

import static org.bankapp.backend.interceptors.AuthorizationInterceptor.CUSTOMER_ID_ATTRIBUTE;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class CustomerCredentialsController {

    private final CustomerCredentialsService customerCredentialsService;
    private final SessionService sessionService;

    @PostMapping("private/credentials/change-password")
    public void changePassword(@RequestAttribute(name = CUSTOMER_ID_ATTRIBUTE) String customerId,
                               @Valid @RequestBody ChangePasswordDTO changePasswordDTO,
                               HttpServletResponse response) {
        customerCredentialsService.changePassword(customerId,
                changePasswordDTO.getPassword(),
                changePasswordDTO.getNewPassword());
        response.addHeader("Set-Cookie",
                sessionService.generateCookie(
                        sessionService.createSession(customerId)
                )
        );
    }


}
