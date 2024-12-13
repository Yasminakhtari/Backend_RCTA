package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.Testimonial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestimonialRepository extends JpaRepository<Testimonial,Long> {

    Optional<Testimonial> findByIdAndBitDeletedFlag(Long id, byte b);

    @Query("SELECT t FROM Testimonial t WHERE t.bitDeletedFlag = 0 ORDER BY t.rating DESC , t.id DESC LIMIT 20")
    List<Testimonial> findAllActiveByRatingAndIdDesc();
}
