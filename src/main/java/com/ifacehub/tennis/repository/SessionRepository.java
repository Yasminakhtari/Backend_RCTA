package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
    List<Session> findAllByOrderByIdDesc();

    List<Session> findByCourseId(Long id);

    @Query(value = "SELECT * FROM session s WHERE :id = ANY (s.players_id)", nativeQuery = true)
    List<Session> findSessionsByPlayersId(Long id);

    List<Session> findByUserIdOrderByIdDesc(Long userId);
}
