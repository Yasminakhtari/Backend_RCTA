package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.Players;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayersRepository extends JpaRepository<Players,Long> {

//    @Query(value = "SELECT p FROM Players p WHERE p.userId = :userId")
//    List<Players> findAllByOrderByIdDesc(Long userId);

    List<Players> findByUserIdOrderByIdDesc(Long userId);
}
