package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.Tennis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TennisRepository extends JpaRepository<Tennis,Long> {

    List<Tennis> findAllByStatus(String status);
    @Query(value = "select category,array_agg(distinct subcategory) as subcategory from tennis group by category;", nativeQuery = true)
    List<Object[]> findAllCategoryAndSubCategory();
}
