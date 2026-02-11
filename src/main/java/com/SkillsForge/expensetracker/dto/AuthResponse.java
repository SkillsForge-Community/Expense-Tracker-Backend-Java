package com.SkillsForge.expensetracker.dto;

import com.SkillsForge.expensetracker.app.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
  // no need for validation, this is output
  private String token; // the JWT token string
  private String type; // token type, always "Bearer"
  private Long id;
  private String username;
  private String email;
  private Role role; // USER or ADMIN
}
