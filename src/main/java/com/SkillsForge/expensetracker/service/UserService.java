package com.SkillsForge.expensetracker.service;

import com.SkillsForge.expensetracker.dto.AuthResponse;
import com.SkillsForge.expensetracker.dto.LoginRequest;
import com.SkillsForge.expensetracker.dto.SignupRequest;
import com.SkillsForge.expensetracker.dto.UserDto;

/**
 * Service interface for user authentication and management
 */
public interface UserService {

    /**
     * Register a new user account
     *
     * @param request signup details (username, email, password)
     * @return authentication response with JWT token
     */
    AuthResponse signup(SignupRequest request);

    /**
     * Authenticate user and generate JWT token
     *
     * @param request login credentials (username, password)
     * @return authentication response with JWT token
     */
    AuthResponse login(LoginRequest request);

    /**
     * Get the currently authenticated user
     *
     * @return user details without password
     */
    UserDto getCurrentUser();
}
