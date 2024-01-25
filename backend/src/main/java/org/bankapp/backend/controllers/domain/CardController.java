package org.bankapp.backend.controllers.domain;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.CardPreviewGetDTO;
import org.bankapp.backend.services.domain.CardService;
import org.bankapp.backend.services.security.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.bankapp.backend.interceptors.AuthorizationInterceptor.CUSTOMER_ID_ATTRIBUTE;

@RestController
@RequestMapping("api/private/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final SessionService sessionService;

    @GetMapping("/{accountNumber}")
    public List<CardPreviewGetDTO> getCardPreviews(@RequestAttribute(name = CUSTOMER_ID_ATTRIBUTE) String customerId,
                                                   @PathVariable String accountNumber,
                                                   HttpServletResponse response) {
        List<CardPreviewGetDTO> cardPreviewGetDTOS = cardService.getCardPreviews(customerId, accountNumber);
        response.addHeader("Set-Cookie",
                sessionService.generateCookie(
                        sessionService.createSession(customerId)
                )
        );
        return cardPreviewGetDTOS;
    }

    @GetMapping("/{accountNumber}/details/{cardId}/card-number")
    public String getCardNumber(@RequestAttribute(name = CUSTOMER_ID_ATTRIBUTE) String customerId,
                                @PathVariable String accountNumber,
                                @PathVariable String cardId,
                                HttpServletResponse response) {
        String cardNumber = cardService.getCardNumber(customerId, accountNumber, cardId);
        response.addHeader("Set-Cookie",
                sessionService.generateCookie(
                        sessionService.createSession(customerId)
                )
        );
        return cardNumber;
    }

    @GetMapping("/{accountNumber}/details/{cardId}/cvv-code")
    public String getCvv(@RequestAttribute(name = CUSTOMER_ID_ATTRIBUTE) String customerId,
                         @PathVariable String cardId,
                         @PathVariable String accountNumber,
                         HttpServletResponse response) {
        String cvv = cardService.getCvv(customerId, accountNumber, cardId);
        response.addHeader("Set-Cookie",
                sessionService.generateCookie(
                        sessionService.createSession(customerId)
                )
        );
        return cvv;
    }

    @GetMapping("/{accountNumber}/details/{cardId}/valid-thru")
    public String getValidThru(@RequestAttribute(name = CUSTOMER_ID_ATTRIBUTE) String customerId,
                               @PathVariable String cardId,
                               @PathVariable String accountNumber,
                               HttpServletResponse response) {
        String validThru = cardService.getValidThru(customerId, accountNumber, cardId);
        response.addHeader("Set-Cookie",
                sessionService.generateCookie(
                        sessionService.createSession(customerId)
                )
        );
        return validThru;
    }


}
