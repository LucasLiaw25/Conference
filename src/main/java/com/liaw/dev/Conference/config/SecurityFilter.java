package com.liaw.dev.Conference.config;

import com.liaw.dev.Conference.dto.JWTDetails;
import com.liaw.dev.Conference.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (Strings.isNotEmpty(header) && header.startsWith("Bearer ")){
            String token = header.substring("Bearer ".length()).trim();
            Optional<JWTDetails> jwtUserData = service.verifyToken(token);

            if (jwtUserData.isPresent()){
                UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(
                        jwtUserData.get(), null, Collections.emptyList()
                );
                SecurityContextHolder.getContext().setAuthentication(userAndPass);
            }
            filterChain.doFilter(request, response);
        }else {
            filterChain.doFilter(request, response);
        }
    }
}
