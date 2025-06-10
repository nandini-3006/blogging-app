package com.scalar.blogapp.users;

import com.scalar.blogapp.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <UserEntity,Long> {
    UserEntity findByUsername(String username);
}
