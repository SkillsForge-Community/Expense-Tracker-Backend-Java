package com.SkillsForge.expensetracker.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;


@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    LocalDateTime createdAt;
    @Column(nullable = false)
    LocalDateTime updatedAt;

    @Override
    public final boolean equals(Object o) {
        // Optimization: If memory addresses match, they are definitely equal.
        if (this == o) return true;

        // Null Check: Standard Java safety.
        if (o == null) return false;

        // REPLACEMENT NOTE:
        // OLD: if (getClass() != o.getClass()) ...
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;

        BaseEntity that = (BaseEntity) o;

        // REPLACEMENT NOTE:
        // OLD: return Objects.nonNull(id) ? id.equals(that.id) : id == that.id;
        // The old code returned TRUE if both IDs were null.
        // FIX: We strictly require ID to be non-null for equality.
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        // REPLACEMENT NOTE:
        // OLD: return Objects.hash(id);.
        // FIX: Return a constant or the Class hash.
        // It hurts performance slightly (O(n) instead of O(1)) but guarantees correctness.
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
