package org.bankapp.backend.controllers.domain;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.CustomerInfoPreviewGetDTO;
import org.bankapp.backend.services.domain.CustomerInfoService;
import org.bankapp.backend.services.security.SessionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.bankapp.backend.interceptors.AuthorizationInterceptor.CUSTOMER_ID_ATTRIBUTE;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/private/customer-info")
public class CustomerInfoController {

    private final CustomerInfoService customerInfoService;
    private final SessionService sessionService;

    @GetMapping()
    public CustomerInfoPreviewGetDTO getCustomerInfoPreview(@RequestAttribute(name = CUSTOMER_ID_ATTRIBUTE) String customerId,
                                                            HttpServletResponse response) {
        CustomerInfoPreviewGetDTO customerInfoPreviewGetDTO = customerInfoService.getCustomerInfoPreview(customerId);
        response.addHeader("Set-Cookie",
                sessionService.generateCookie(
                        sessionService.createSession(customerId)
                )
        );
        return customerInfoPreviewGetDTO;
    }

    @GetMapping("/details/pesel")
    public String getPesel(@RequestAttribute(name = CUSTOMER_ID_ATTRIBUTE) String customerId,
                           HttpServletResponse response) {
        String pesel = customerInfoService.getPesel(customerId);
        response.addHeader("Set-Cookie",
                sessionService.generateCookie(
                        sessionService.createSession(customerId)
                )
        );
        return pesel;
    }

    @GetMapping("/details/identity-document-number")
    public String getIdentityDocumentNumber(@RequestAttribute(name = CUSTOMER_ID_ATTRIBUTE) String customerId,
                                            HttpServletResponse response) {
        String identityDocumentNumber = customerInfoService.getIdentityDocumentNumber(customerId);
        response.addHeader("Set-Cookie",
                sessionService.generateCookie(
                        sessionService.createSession(customerId)
                )
        );
        return identityDocumentNumber;
    }

}
