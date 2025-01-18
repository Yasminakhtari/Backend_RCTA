package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {
    List<Location> findAllByOrderByIdDesc();
}
