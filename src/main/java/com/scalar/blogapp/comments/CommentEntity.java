package com.scalar.blogapp.comments;
import com.scalar.blogapp.articles.ArticleEntity;
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
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    @Nullable
    private String title;
    @NonNull
    private String body;
    @CreatedDate
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name="articleId",nullable = false)
    private ArticleEntity article;
    @ManyToOne
    @JoinColumn(name="authorId",nullable = false)
    private UserEntity author;
}
