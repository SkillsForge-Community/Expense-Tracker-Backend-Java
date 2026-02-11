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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Enables @PreAuthorize, @PostAuthorize annotations
@RequiredArgsConstructor
public class Config {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // Disable CSRF (Cross-Site Request Forgery) protection
        // We use stateless JWT, so CSRF is not needed
        .csrf(AbstractHttpConfigurer::disable)

        // Configure endpoint authorization
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/api/v1/auth/signup",
                            "/api/v1/auth/login",
                            "/api/v1/auth/refresh-token" // if you have one
                    ).permitAll()

                    //  LOCKED DOORS (Everything else, including /auth/current-user)
                    .anyRequest().authenticated()
            )
        // Configure session management
        // STATELESS = no server-side sessions, use JWT only
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // Add our JWT filter before Spring Security's authentication filter
        // This ensures JWT validation happens before standard authentication
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}
