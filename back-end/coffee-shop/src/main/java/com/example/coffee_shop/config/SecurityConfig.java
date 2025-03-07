package com.example.coffee_shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for API simplicity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register").permitAll() // Allow public access to registration
                        .requestMatchers("/auth/login").permitAll() // Allow public access to login
                        .requestMatchers("/api/products").permitAll()
                        .anyRequest().authenticated() // Require authentication for all other endpoints
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(401); // Set HTTP status to 401 (Unauthorized)
                            response.getWriter().write("Unauthorized"); // Write response body
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout") // Configure custom logout URL
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(200); // Set HTTP status to 200 (OK)
                            response.getWriter().write("Logout successful"); // Write response body
                        })
                        .invalidateHttpSession(true) // Invalidate session on logout
                        .deleteCookies("JSESSIONID") // Delete cookies on logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password hashing
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}