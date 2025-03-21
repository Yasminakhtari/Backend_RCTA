package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
	
    List<Order> findByUserId(Long userId);
    List<Order> findAllByOrderByIdDesc();
    @Query(value = """
    SELECT o.user_id
    FROM orders o,
    LATERAL jsonb_array_elements(o.items::jsonb) item
    WHERE item->>'groups' = 'Classes'
    AND (item->>'id')::BIGINT = :sessionId
    AND item->>'fromDate' = :fromDate
    AND item->>'toDate' = :toDate
""", nativeQuery = true)

    List<Long> findUserIdsBySessionId(Long sessionId, String fromDate, String toDate);

    @Query("SELECT DISTINCT o.userId FROM Order o")
    List<Long> findAllUserIds();

}
