package com.scalar.blogapp.articles;
import com.scalar.blogapp.users.UserEntity;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import lombok.*;

import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import java.util.Date;
import java.util.Objects;
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    @NonNull
    private String title;
    @NonNull
    @Column(unique = true)
    private String slug;
    @Nullable
    private String subtitle;
    @NonNull
    private String body;
    @CreatedDate
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name="authorId",nullable=false)
    private UserEntity author;
}
