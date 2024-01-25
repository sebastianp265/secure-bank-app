package org.bankapp.backend.controllers.security;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.ChangePasswordDTO;
import org.bankapp.backend.dtos.LoginRequestDTO;
import org.bankapp.backend.dtos.RequestLoginResponseDTO;
import org.bankapp.backend.services.security.AuthService;
import org.bankapp.backend.services.security.SessionService;
import org.springframework.web.bind.annotation.*;

import static org.bankapp.backend.interceptors.AuthorizationInterceptor.CUSTOMER_ID_ATTRIBUTE;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("public/auth/login")
    public void login(@Valid @RequestBody LoginRequestDTO loginRequestDTO,
                      HttpServletResponse response) {
        authService.login(
                loginRequestDTO.getCustomerId(),
                loginRequestDTO.getPassword(),
                response);
    }

    @PostMapping("public/auth/request-login/{customerId}")
    public RequestLoginResponseDTO requestLogin(@PathVariable String customerId) {
        return authService.requestLogin(customerId);
    }

    @PostMapping("private/auth/logout")
    public void logout(@RequestAttribute(name = CUSTOMER_ID_ATTRIBUTE) String customerId,
                       HttpServletResponse response) {
        authService.logout(customerId, response);
    }
}
