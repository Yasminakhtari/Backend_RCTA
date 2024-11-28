package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.AcademyMilestones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademyMilestonesRepository extends JpaRepository<AcademyMilestones,Long> {

    Optional<AcademyMilestones> findByIdAndBitDeletedFlag(Long id, byte b);

    @Query(value = "SELECT v FROM AcademyMilestones v WHERE v.bitDeletedFlag = 0")
    List<AcademyMilestones> findAllActive();
}
