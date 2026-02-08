package com.SkillsForge.expensetracker.service;

import com.SkillsForge.expensetracker.app.enums.Role;
import com.SkillsForge.expensetracker.dto.AuthResponse;
import com.SkillsForge.expensetracker.dto.LoginRequest;
import com.SkillsForge.expensetracker.dto.SignupRequest;
import com.SkillsForge.expensetracker.dto.UserDto;
import com.SkillsForge.expensetracker.exception.EmailAlreadyExistsException;
import com.SkillsForge.expensetracker.exception.InvalidCredentialsException;
import com.SkillsForge.expensetracker.exception.UsernameAlreadyExistsException;
import com.SkillsForge.expensetracker.persistence.entity.User;
import com.SkillsForge.expensetracker.persistence.repository.UserRepository;
import com.SkillsForge.expensetracker.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public AuthResponse signup(SignupRequest request) {
        log.info("Attempting to register new user: {}", request.getUsername());

        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("Signup failed: username already exists - {}", request.getUsername());
            throw new UsernameAlreadyExistsException(
                    "Username already taken: " + request.getUsername());
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Signup failed: email already exists - {}", request.getEmail());
            throw new EmailAlreadyExistsException("Email already registered: " + request.getEmail());
        }

        // Create new user with hashed password
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // Hash password with BCrypt
                .role(Role.USER) // Default role
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        // Save user to database
        User savedUser = userRepository.save(user);
        log.info("Successfully registered new user with ID: {}", savedUser.getId());

        // Generate JWT token for automatic login
        String token = jwtUtil.generateToken(savedUser.getUsername());

        // Build and return authentication response
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        log.info("Login attempt for user: {}", request.getUsername());

        try {
            // Authenticate user credentials
            // Spring Security will compare plain password with BCrypt hash
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException e) {
            log.warn("Login failed for user: {} - Invalid credentials", request.getUsername());
            throw new InvalidCredentialsException("Invalid username or password");
        }

        // Load user from database (authentication successful)
        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        log.info("User {} logged in successfully", user.getUsername());

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername());

        // Build and return authentication response
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }


    @Override
    public UserDto getCurrentUser() {
        // Get authentication from SecurityContext (set by JWT filter)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        log.debug("Fetching current user: {}", username);

        // Load user from database
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Convert to DTO (excludes password)
        return UserDto.from(user);
    }
}
