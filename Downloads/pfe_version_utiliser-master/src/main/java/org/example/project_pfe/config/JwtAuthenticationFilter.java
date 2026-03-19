package org.example.project_pfe.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path   = request.getRequestURI();
        String method = request.getMethod();
        System.out.println(">>> REQUEST: " + method + " " + path);

        if (path.startsWith("/api/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 1. Try Authorization header first
        String authHeader = request.getHeader("Authorization");

        // 2. Fallback: ?token= query param (used for direct browser downloads)
        if ((authHeader == null || !authHeader.startsWith("Bearer ")) ) {
            String tokenParam = request.getParameter("token");
            if (tokenParam != null && !tokenParam.isBlank()) {
                authHeader = "Bearer " + tokenParam;
            }
        }

        System.out.println(">>> AUTH HEADER: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            boolean valid = jwtUtil.isTokenValid(token);
            System.out.println(">>> TOKEN VALID: " + valid);

            if (valid) {
                String email = jwtUtil.extractEmail(token);
                String role  = jwtUtil.extractRole(token);
                System.out.println(">>> EMAIL: " + email + " | ROLE: " + role);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.println(">>> AUTH SET: ROLE_" + role);
            }
        } else {
            System.out.println(">>> NO VALID AUTH HEADER");
        }

        filterChain.doFilter(request, response);
    }
}