package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaSpecificationExecutor<User>,JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByIdAndBitDeletedFlag(Long id, byte b);
}
