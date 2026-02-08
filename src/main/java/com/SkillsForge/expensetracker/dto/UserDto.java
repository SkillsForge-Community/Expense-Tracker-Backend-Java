package com.SkillsForge.expensetracker.dto;

import com.SkillsForge.expensetracker.app.enums.Role;
import com.SkillsForge.expensetracker.persistence.entity.User;

import java.time.LocalDateTime;

public record UserDto(
    Long id,
    String username,
    String email,
    Role role,
    boolean enabled,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
){
    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.isEnabled(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
