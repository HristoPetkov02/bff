package com.tinqinacademy.bff.rest.security;

import com.tinqinacademy.auth.api.operations.validatejwt.ValidateJwtOutput;
import com.tinqinacademy.auth.restexport.AuthRestExport;
import com.tinqinacademy.bff.rest.context.UserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final AuthRestExport authRestExport;
    private final UserContext userContext;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        ValidateJwtOutput output = authRestExport.validateJwt(authHeader);
        String token = getToken(request);
        if (token != null && !token.isEmpty() && output.getIsValid()) {
            try {
                Map<String, Object> payloadMap = jwtDecoder.decodeJwt(token);

                String id = (String) payloadMap.get("sub");
                String role = (String) payloadMap.get("role");
                GrantedAuthority authority = new SimpleGrantedAuthority(role);

                CustomAuthenticationToken authenticationToken = new CustomAuthenticationToken(
                        id, List.of(authority)
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                userContext.setUserId(id);
            } catch (Exception e) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        filterChain.doFilter(request, response);

    }

    private String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
