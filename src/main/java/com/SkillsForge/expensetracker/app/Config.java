package com.SkillsForge.expensetracker.app;

import com.SkillsForge.expensetracker.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security Configuration Configures authentication, authorization, and JWT token validation
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Enables @PreAuthorize, @PostAuthorize annotations
@RequiredArgsConstructor
public class Config {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  /**
   * Configure the security filter chain Defines which endpoints are public and which require
   * authentication
   *
   * @param http HttpSecurity to configure
   * @return configured SecurityFilterChain
   * @throws Exception if configuration fails
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // Disable CSRF (Cross-Site Request Forgery) protection
        // We use stateless JWT, so CSRF is not needed
        .csrf(AbstractHttpConfigurer::disable)

        // Configure endpoint authorization
        .authorizeHttpRequests(
            auth ->
                auth
                    // Public endpoints - anyone can access
                    .requestMatchers("/api/auth/**")
                    .permitAll()

                    // All other endpoints require authentication
                    .anyRequest()
                    .authenticated())

        // Configure session management
        // STATELESS = no server-side sessions, use JWT only
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // Add our JWT filter before Spring Security's authentication filter
        // This ensures JWT validation happens before standard authentication
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /**
   * Password encoder bean for hashing passwords BCrypt is a strong hashing algorithm recommended
   * for passwords
   *
   * @return BCryptPasswordEncoder instance
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Authentication manager bean Used for authenticating users during login
   *
   * @param config authentication configuration
   * @return AuthenticationManager
   * @throws Exception if configuration fails
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}
