package com.scalar.blogapp.users;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    @Column( nullable = false)
    @NonNull
    private String username;
    @Column( nullable = false)
    @NonNull
    private String email;
    @Column( nullable = true)
    @Nullable
    private String bio;
    @Column( nullable = true)
    @Nullable
    private String image;

}