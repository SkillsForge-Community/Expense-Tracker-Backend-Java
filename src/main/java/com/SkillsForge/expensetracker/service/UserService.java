package com.SkillsForge.expensetracker.service;

import com.SkillsForge.expensetracker.dto.AuthResponse;
import com.SkillsForge.expensetracker.dto.LoginRequest;
import com.SkillsForge.expensetracker.dto.SignupRequest;
import com.SkillsForge.expensetracker.dto.UserDto;

public interface UserService {


    AuthResponse signup(SignupRequest request);


    AuthResponse login(LoginRequest request);

    UserDto getCurrentUser();
}
