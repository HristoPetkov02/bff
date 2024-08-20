package com.tinqinacademy.bff.rest.config;

import com.tinqinacademy.bff.api.exceptions.BffApiException;
import com.tinqinacademy.bff.api.restroutes.BffRestApiRoutes;
import com.tinqinacademy.bff.rest.security.JwtAuthenticationEntryPoint;
import com.tinqinacademy.bff.rest.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
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

    @Bean
    public UserDetailsService emptyDetailsService() {
        // This will stop spring security to create a password from console
        return username -> {
            throw new BffApiException("No local users, only JWT tokens allowed.", HttpStatus.NOT_FOUND);
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                    configureUserAccess(authorize);
                    configureAdminAccess(authorize);
                    authorize.anyRequest().permitAll();
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    private void configureUserAccess(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry request){
        request.requestMatchers(HttpMethod.POST,BffRestApiRoutes.API_AUTH_CHECK_JWT).hasAnyAuthority("USER", "ADMIN");
        request.requestMatchers(HttpMethod.POST,BffRestApiRoutes.HOTEL_API_HOTEL_BOOK_ROOM).hasAnyAuthority("USER", "ADMIN");
        request.requestMatchers(HttpMethod.DELETE,BffRestApiRoutes.HOTEL_API_HOTEL_UNBOOK_ROOM).hasAnyAuthority("USER", "ADMIN");
        request.requestMatchers(HttpMethod.POST,BffRestApiRoutes.COMMENTS_API_HOTEL_LEAVE_COMMENT).hasAnyAuthority("USER", "ADMIN");
        request.requestMatchers(HttpMethod.PATCH,BffRestApiRoutes.COMMENTS_API_HOTEL_UPDATE_OWN_COMMENT).hasAnyAuthority("USER", "ADMIN");
    }

    private void configureAdminAccess(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry request){
        request.requestMatchers(HttpMethod.POST,BffRestApiRoutes.HOTEL_API_SYSTEM_ADD_ROOM).hasAuthority("ADMIN");
        request.requestMatchers(HttpMethod.POST,BffRestApiRoutes.HOTEL_API_SYSTEM_REGISTER_VISITOR).hasAuthority("ADMIN");
        request.requestMatchers(HttpMethod.GET,BffRestApiRoutes.HOTEL_API_SYSTEM_VISITOR_REPORT).hasAuthority("ADMIN");
        request.requestMatchers(HttpMethod.PUT,BffRestApiRoutes.HOTEL_API_SYSTEM_UPDATE_ROOM).hasAuthority("ADMIN");
        request.requestMatchers(HttpMethod.PATCH,BffRestApiRoutes.HOTEL_API_SYSTEM_UPDATE_PARTIALLY_ROOM).hasAuthority("ADMIN");
        request.requestMatchers(HttpMethod.DELETE,BffRestApiRoutes.HOTEL_API_SYSTEM_DELETE_ROOM).hasAuthority("ADMIN");
        request.requestMatchers(HttpMethod.PUT,BffRestApiRoutes.COMMENTS_API_SYSTEM_UPDATE_COMMENT).hasAuthority("ADMIN");
        request.requestMatchers(HttpMethod.DELETE,BffRestApiRoutes.COMMENTS_API_SYSTEM_DELETE_COMMENT).hasAuthority("ADMIN");
    }

}
