package com.scalar.blogapp.articles;


import com.scalar.blogapp.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository <ArticleEntity,Long> {
    Optional<ArticleEntity> findBySlug(String slug);
}
