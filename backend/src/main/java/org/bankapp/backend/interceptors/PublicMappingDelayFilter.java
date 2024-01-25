package org.bankapp.backend.interceptors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class PublicMappingDelayFilter extends OncePerRequestFilter {

    private static final long MIN_DELAY_BASE_MS = 2000;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().startsWith("/private") && !request.getMethod().equals("OPTIONS")) {
            long startTime = System.currentTimeMillis();
            filterChain.doFilter(request, response);
            long endTime = System.currentTimeMillis();

            long requestTime = endTime - startTime;

            long delayTime = (requestTime / MIN_DELAY_BASE_MS + 1) * MIN_DELAY_BASE_MS;
            try {
                Thread.sleep(delayTime);
            } catch (InterruptedException e) {
                log.error("Error while delaying request", e);
            }
            return;
        }

        filterChain.doFilter(request, response);
    }
}
