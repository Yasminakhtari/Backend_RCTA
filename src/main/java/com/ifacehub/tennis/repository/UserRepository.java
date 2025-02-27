package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaSpecificationExecutor<User>,JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByIdAndBitDeletedFlag(Long id, byte b);
    
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT v FROM User v WHERE v.bitDeletedFlag = 0")
    List<User> findAllActive();
}
