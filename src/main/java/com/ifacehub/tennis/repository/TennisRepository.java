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
    
    //Check it 
    List<Tennis> findByGroups(String group);
    
    List<Tennis> findByCategory(String category);
    
    List<Tennis> findBySubcategory(String subcategory);
    
    List<Tennis> findByGroupsAndCategory(String group,String category);
    
    @Query("SELECT DISTINCT t.groups FROM Tennis t")
    List<String> findAllGroups();
    
    /////////////
    List<Tennis> findByGroupsAndCategoryAndSubcategory(String group, String category, String subcategory);
    
    
    @Query("SELECT t FROM Tennis t WHERE " +
    	       "(:group IS NULL OR t.groups = :group) AND " +
    	       "(:category IS NULL OR t.category = :category) AND " +
    	       "(:subcategory IS NULL OR t.subcategory = :subcategory)")
    	List<Tennis> findFilteredTennis(@org.springframework.lang.Nullable String group,
    	                                @org.springframework.lang.Nullable String category,
    	                                @org.springframework.lang.Nullable String subcategory);

    
}
