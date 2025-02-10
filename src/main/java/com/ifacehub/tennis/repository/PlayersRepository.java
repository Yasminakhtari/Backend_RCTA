package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.Players;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayersRepository extends JpaRepository<Players, Long> {

//    @Query(value = "SELECT p FROM Players p WHERE p.userId = :userId")
//    List<Players> findAllByOrderByIdDesc(Long userId);

//    List<Players> findByUserIdOrderByIdDesc(Long userId);

//    List<Players> findByUserIdOrderByIdDescAndBitDeletedFlag(Long userId, byte b);

    Optional<Players> findByIdAndBitDeletedFlag(Long id, byte b);

    List<Players> findByUserIdAndBitDeletedFlagOrderByIdDesc(Long userId, byte b);

    //    List<String> findPlayerNamesByIds(List<Long> existingPlayers);
    @Query("SELECT p.name FROM Players p WHERE p.id IN :playerIds")
    List<String> findNameByIds(@Param("playerIds") List<Long> playerIds);
}
