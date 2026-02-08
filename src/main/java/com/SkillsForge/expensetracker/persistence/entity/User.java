package com.SkillsForge.expensetracker.persistence.entity;

import com.SkillsForge.expensetracker.app.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Accessors(chain=true)
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@SuperBuilder
@RequiredArgsConstructor
public class User extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role;

    @Column(nullable = false)
    @Builder.Default
    private boolean enabled =  true;

    @Column(nullable = false)
    @Builder.Default
    private boolean accountNonExpired = true;

    @Column(nullable = false)
    @Builder.Default
    private boolean accountNonLocked = true;

    @Column(nullable = false)
    @Builder.Default
    private boolean credentialsNonExpired =  true;
}
