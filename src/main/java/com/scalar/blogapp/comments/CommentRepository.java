package com.scalar.blogapp.comments;

import com.scalar.blogapp.comments.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository <CommentEntity,Long> {
}