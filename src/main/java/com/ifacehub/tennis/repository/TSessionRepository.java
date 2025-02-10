package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.TSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TSessionRepository extends JpaRepository<TSession, Long> {
    Optional<TSession> findBySessionIdAndPlayersId(Long sessionId, List<Long> playersId);

    @Query(value = "SELECT DISTINCT players_id FROM tsession sp, " +
            "LATERAL UNNEST(sp.players_id) AS player_id " +
            "WHERE sp.session_id = :sessionId " +
            "AND sp.course_id = :courseId " +
            "AND player_id = ANY(:playersArray)",
            nativeQuery = true)
    List<Long> findExistingPlayersInSession(@Param("sessionId") Long sessionId,
                                            @Param("courseId") Long courseId,
                                            @Param("playersArray") Long[] playersArray);

    @Query(value = "SELECT DISTINCT sp.players_id FROM tsession sp " +
            "WHERE sp.players_id IN :playersId",
            nativeQuery = true)
    List<Long> findPlayersInAnySession(@Param("playersId") List<Long> playersId);

    @Query(value = "SELECT DISTINCT player_id FROM tsession sp, " +
            "LATERAL UNNEST(sp.players_id) AS player_id " +
            "WHERE player_id = ANY(:playersArray) " +
            "AND sp.session_id <> :sessionId",
            nativeQuery = true)
    List<Long> findPlayersInOtherSessions(@Param("sessionId") Long sessionId,
                                          @Param("playersArray") Long[] playersArray);  // 🔹 Change List<Long> to Long[]

    boolean existsBySessionId(Long sessionId);

    Optional<TSession> findBySessionIdAndCourseId(Long sessionId, Long courseId);
}
