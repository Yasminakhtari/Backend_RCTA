package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.NotificationUsers;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationUsersRepository extends JpaRepository<NotificationUsers,Long> {
    // @Query("SELECT nu FROM NotificationUsers nu WHERE nu.userId = :userId")
    // List<NotificationUsers> findByUserId(Long userId);

    // @Modifying
    // @Transactional
    // @Query("UPDATE NotificationUsers nu SET nu.status = 'read' WHERE nu.notification.id = :notificationId AND nu.userId = :userId")
    // void markNotificationAsRead(Long notificationId, Long userId);
    // 1. Add pagination support
    Page<NotificationUsers> findByUserId(Long userId, Pageable pageable);
    
    // 2. Find unread notifications
    List<NotificationUsers> findByUserIdAndStatus(Long userId, String status);
    
    // 3. Count unread notifications
    @Query("SELECT COUNT(nu) FROM NotificationUsers nu WHERE nu.userId = :userId AND nu.status = 'unread'")
    long countUnreadNotifications(Long userId);

    // 4. Bulk mark as read
    @Modifying
    @Transactional
    @Query("UPDATE NotificationUsers nu SET nu.status = 'read' WHERE nu.userId = :userId")
    int markAllAsReadForUser(Long userId);

    // 5. Native query example for complex operations
    @Query(value = """
        SELECT nu.* FROM notification_users nu
        JOIN notification n ON nu.notification_id = n.id
        WHERE nu.user_id = :userId
        AND n.created_on >= CURRENT_DATE - INTERVAL '7 days'
        """, nativeQuery = true)
    List<NotificationUsers> findRecentNotifications(Long userId);

    // 6. Delete operations
    void deleteByUserId(Long userId);
    void deleteByNotificationId(Long notificationId);

    // Existing methods
    @Query("SELECT nu FROM NotificationUsers nu WHERE nu.userId = :userId")
    List<NotificationUsers> findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE NotificationUsers nu SET nu.status = 'read' WHERE nu.notification.id = :notificationId AND nu.userId = :userId")
    void markNotificationAsRead(Long notificationId, Long userId);
}
