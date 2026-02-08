package com.SkillsForge.expensetracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    // Extract the Authorization header from the request
    final String authorizationHeader = request.getHeader("Authorization");

    String username = null;
    String jwt = null;

    // Check if header exists and starts with "Bearer "
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7); // Remove "Bearer " prefix to get token
      try {
        username = jwtUtil.extractUsername(jwt); // Extract username from token
      } catch (Exception e) {
        // Token is invalid or expired - log and continue without authentication
        logger.warn("JWT token extraction failed: " + e.getMessage());
      }
    }

    // If we have a username and no authentication exists in SecurityContext
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      // Load user details from database
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);

      // Validate the token
      if (jwtUtil.isTokenValid(jwt, userDetails)) {
        // Token is valid - create authentication object
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        // Set additional details (IP address, session ID, etc.)
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Set authentication in SecurityContext
        // Now Spring Security knows this request is authenticated
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }

    // Continue the filter chain (pass request to next filter or controller)
    filterChain.doFilter(request, response);
  }
}
