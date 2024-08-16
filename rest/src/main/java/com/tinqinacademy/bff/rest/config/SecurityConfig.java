package com.tinqinacademy.bff.rest.config;

import com.tinqinacademy.bff.api.exceptions.BffApiException;
import com.tinqinacademy.bff.api.restroutes.BffRestApiRoutes;
import com.tinqinacademy.bff.rest.security.JwtAuthenticationEntryPoint;
import com.tinqinacademy.bff.rest.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final String[] USER_URLS = {
            BffRestApiRoutes.API_AUTH_CHECK_JWT,
            BffRestApiRoutes.HOTEL_API_HOTEL_BOOK_ROOM,
            BffRestApiRoutes.HOTEL_API_HOTEL_UNBOOK_ROOM
    };

    private final String[] ADMIN_URLS = {
            BffRestApiRoutes.HOTEL_API_SYSTEM_ADD_ROOM,
            BffRestApiRoutes.HOTEL_API_SYSTEM_REGISTER_VISITOR,
            BffRestApiRoutes.HOTEL_API_SYSTEM_VISITOR_REPORT,
            BffRestApiRoutes.HOTEL_API_SYSTEM_UPDATE_ROOM,
            BffRestApiRoutes.HOTEL_API_SYSTEM_UPDATE_PARTIALLY_ROOM,
            BffRestApiRoutes.HOTEL_API_SYSTEM_DELETE_ROOM
    };

    private final String[] PUBLIC_URLS = {
            BffRestApiRoutes.HOTEL_API_HOTEL_CHECK_AVAILABILITY,
            BffRestApiRoutes.HOTEL_API_HOTEL_GET_ROOM
    };

    @Bean
    public UserDetailsService emptyDetailsService() {
        // This will stop spring security to create a password from console
        return username -> {
            throw new BffApiException("No local users, only JWT tokens allowed.", HttpStatus.NOT_FOUND);
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(PUBLIC_URLS).permitAll();
                    authorize.requestMatchers(ADMIN_URLS).hasAuthority("ADMIN");
                    authorize.requestMatchers(USER_URLS).hasAnyAuthority("USER", "ADMIN");
                    authorize.anyRequest().permitAll();
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
