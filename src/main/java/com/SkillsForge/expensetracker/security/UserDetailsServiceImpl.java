package com.SkillsForge.expensetracker.security;

import com.SkillsForge.expensetracker.persistence.entity.User;
import com.SkillsForge.expensetracker.persistence.repository.UserRepository;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of Spring Security's UserDetailsService. Loads user details from database for
 * authentication.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  /**
   * Load user by username for Spring Security authentication. This method is called by Spring
   * Security during authentication.
   *
   * @param username the username to search for
   * @return UserDetails object containing user information
   * @throws UsernameNotFoundException if user not found
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Find user in database
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username));

    // Convert our User entity to Spring Security's UserDetails
    return buildUserDetails(user);
  }

  /**
   * Build Spring Security UserDetails from our User entity
   *
   * @param user our User entity
   * @return Spring Security UserDetails
   */
  private UserDetails buildUserDetails(User user) {
    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getUsername())
        .password(user.getPassword()) // BCrypt hashed password
        .authorities(getAuthorities(user)) // Convert role to authorities
        .accountExpired(!user.isAccountNonExpired())
        .accountLocked(!user.isAccountNonLocked())
        .credentialsExpired(!user.isCredentialsNonExpired())
        .disabled(!user.isEnabled())
        .build();
  }

  /**
   * Get authorities (permissions) for the user based on their role Spring Security uses
   * GrantedAuthority for permissions
   *
   * @param user the user entity
   * @return collection of granted authorities
   */
  private Collection<? extends GrantedAuthority> getAuthorities(User user) {
    Set<GrantedAuthority> authorities = new HashSet<>();

    // Add role as authority (Spring Security expects "ROLE_" prefix)
    authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

    // Add permissions from the role
    user.getRole()
        .getPermissions()
        .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.name())));

    return authorities;
  }
}
