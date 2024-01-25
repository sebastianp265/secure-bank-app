package org.bankapp.backend.interceptors;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bankapp.backend.exceptions.SessionExpiredException;
import org.bankapp.backend.services.security.SessionService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {
    public static final String CUSTOMER_ID_ATTRIBUTE = SessionService.SESSION_COOKIE_NAME;
    private final SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("OPTIONS")) {
            return true;
        }
        if (isAuthorized(request)) {
            return true;
        } else {
            throw new SessionExpiredException();
        }
    }

    private boolean isAuthorized(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return false;
        }
        if (request.getAttribute(CUSTOMER_ID_ATTRIBUTE) != null) {
            return false;
        }

        for (Cookie cookie : cookies) {
            log.debug("Cookie: {}", cookie.getName());
            if (cookie.getName().equals(SessionService.SESSION_COOKIE_NAME)) {
                String customerId = sessionService.authorizeCustomer(cookie.getValue());
                request.setAttribute(CUSTOMER_ID_ATTRIBUTE, customerId);
                return true;
            }
        }
        return false;
    }
}
