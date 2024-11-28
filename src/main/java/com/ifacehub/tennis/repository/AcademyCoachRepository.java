package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.AcademyCoach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademyCoachRepository extends JpaRepository<AcademyCoach,Long> {
    Optional<AcademyCoach> findByIdAndBitDeletedFlag(Long id, byte b);
    @Query(value = "SELECT ac FROM AcademyCoach ac WHERE ac.bitDeletedFlag = 0")
    List<AcademyCoach> findAllActive();
}
